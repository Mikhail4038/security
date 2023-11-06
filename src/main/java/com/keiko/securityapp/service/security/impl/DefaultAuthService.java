package com.keiko.securityapp.service.security.impl;

import com.keiko.securityapp.entity.jwt.JwtRefreshToken;
import com.keiko.securityapp.entity.request.BlockUserRequest;
import com.keiko.securityapp.entity.request.LoginRequest;
import com.keiko.securityapp.entity.response.JwtResponse;
import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.entity.security.User;
import com.keiko.securityapp.entity.security.VerificationToken;
import com.keiko.securityapp.exception.model.UserAlreadyExistException;
import com.keiko.securityapp.exception.model.UserNotFoundException;
import com.keiko.securityapp.exception.model.VerificationTokenProcessException;
import com.keiko.securityapp.service.basic.RoleService;
import com.keiko.securityapp.service.basic.impl.DefaultUserService;
import com.keiko.securityapp.service.jwt.JwtProvider;
import com.keiko.securityapp.service.jwt.JwtRefreshTokenService;
import com.keiko.securityapp.service.security.AuthService;
import com.keiko.securityapp.service.security.VerificationTokenService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class DefaultAuthService implements AuthService {

    @Autowired
    private DefaultUserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtRefreshTokenService jwtRefreshTokenService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Override
    public void register (@NonNull User user) {
        final String email = user.getEmail ();
        if (emailExists (email)) {
            throw new UserAlreadyExistException (String.format (
                    "There is an account with that email address: %s", email));
        }
        setRole (user);
        userService.register (user);
    }

    @Override
    public JwtResponse login (@NonNull LoginRequest loginRequest) {
        final String email = loginRequest.getEmail ();
        final User user = userService.findByEmail (email);

        if (!user.isEnabled ()) {
            throw new LockedException (
                    "The user's email verification to confirm their account wasn't or was unsuccessful");
        }

        verifyPassword (loginRequest, user);

        final String accessToken = jwtProvider.generateAccessToken (user);
        final String refreshToken = jwtProvider.generateRefreshToken (user);
        final JwtRefreshToken actualJwtRefreshToken = new JwtRefreshToken (email, refreshToken);

        jwtRefreshTokenService.save (actualJwtRefreshToken);
        return new JwtResponse (accessToken, refreshToken);
    }

    @Override
    @Transactional
    public void blockUser (@NonNull BlockUserRequest blockUserRequest) {
        final String email = blockUserRequest.getEmail ();
        jwtRefreshTokenService.delete (email);
        userService.deleteByEmail (email);
    }

    @Override
    public void confirmRegistration (String token) {
        VerificationToken verificationToken = verificationTokenService.findByToken (token);
        User user = verificationToken.getUser ();
        if (validateToken (verificationToken)) {
            throw new VerificationTokenProcessException (
                    String.format ("VerificationToken for user: %s,expired", user));
        }
        user.setEnabled (true);
        userService.save (user);
    }

    private void setRole (User user) {
        Role role = roleService.findByName ("USER");
        user.setRoles (Set.of (role));
    }

    private boolean emailExists (String email) {
        try {
            userService.findByEmail (email);
        } catch (UserNotFoundException ex) {
            return false;
        }
        return true;
    }

    private void verifyPassword (LoginRequest loginRequest, User user) {
        final String presentedPassword = loginRequest.getPassword ();
        final String savedPassword = user.getPassword ();

        if (!BCrypt.checkpw (presentedPassword, savedPassword)) {
            String message = String.format ("Entered incorrect password: %s", presentedPassword);
            throw new BadCredentialsException (message);
        }
    }

    private boolean validateToken (VerificationToken verificationToken) {
        LocalDateTime now = LocalDateTime.now ();
        LocalDateTime toValid = verificationToken.getExpiryDate ().toLocalDateTime ();
        return !now.isAfter (toValid);
    }

}
