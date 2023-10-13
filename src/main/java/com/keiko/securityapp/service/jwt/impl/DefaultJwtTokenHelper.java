package com.keiko.securityapp.service.jwt.impl;

import com.keiko.securityapp.entity.security.User;
import com.keiko.securityapp.entity.jwt.JwtBlockUserRequest;
import com.keiko.securityapp.entity.jwt.JwtRefreshRequest;
import com.keiko.securityapp.entity.jwt.JwtRequest;
import com.keiko.securityapp.entity.jwt.JwtResponse;
import com.keiko.securityapp.entity.redis.JwtRefreshToken;
import com.keiko.securityapp.service.common.UserService;
import com.keiko.securityapp.service.jwt.JwtProvider;
import com.keiko.securityapp.service.jwt.JwtTokenHelper;
import com.keiko.securityapp.service.redis.RefreshTokenService;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultJwtTokenHelper implements JwtTokenHelper {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public JwtResponse login (JwtRequest jwtRequest) {
        final String email = jwtRequest.getEmail ();
        final User user = userService.findUserByEmail (email);

        verifyPassword (jwtRequest, user);

        final String accessToken = jwtProvider.generateAccessToken (user);
        final String refreshToken = jwtProvider.generateRefreshToken (user);
        final JwtRefreshToken actualJwtRefreshToken = new JwtRefreshToken (email, refreshToken);

        refreshTokenService.save (actualJwtRefreshToken);
        return new JwtResponse (accessToken, refreshToken);
    }

    @Override
    public JwtResponse getAccessToken (JwtRefreshRequest jwtRefreshRequest) {
        final String presentedRefreshToken = jwtRefreshRequest.getRefreshToken ();

        if (jwtProvider.validateRefreshToken (presentedRefreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims (presentedRefreshToken);
            final String email = claims.getSubject ();

            if (verifyRefreshToken (presentedRefreshToken, email)) {

                User user = userService.findUserByEmail (email);
                final String newAccessToken = jwtProvider.generateAccessToken (user);
                return new JwtResponse (newAccessToken, null);
            }
        }
        return new JwtResponse (null, null);
    }

    @Override
    public JwtResponse getRefreshToken (JwtRefreshRequest jwtRefreshRequest) {
        final String presentedRefreshToken = jwtRefreshRequest.getRefreshToken ();

        if (jwtProvider.validateRefreshToken (presentedRefreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims (presentedRefreshToken);
            final String email = claims.getSubject ();

            if (verifyRefreshToken (presentedRefreshToken, email)) {

                final User user = userService.findUserByEmail (email);
                final String newRefreshToken = jwtProvider.generateRefreshToken (user);
                final String newAccessToken = jwtProvider.generateAccessToken (user);
                refreshTokenService.save (new JwtRefreshToken (email, newRefreshToken));

                return new JwtResponse (newAccessToken, newRefreshToken);
            }
        }
        return new JwtResponse (null, null);
    }

    @Override
    @Transactional
    public void blockUser (JwtBlockUserRequest jwtBlockUserRequest) {
        final String email = jwtBlockUserRequest.getEmail ();
        refreshTokenService.delete (email);
        userService.deleteByEmail (email);
    }

    private void verifyPassword (JwtRequest jwtRequest, User user) {
        final String presentedPassword = jwtRequest.getPassword ();
        final String savedPassword = user.getPassword ();

        if (!passwordEncoder.matches (presentedPassword, savedPassword)) {
            String message = String.format ("Entered incorrect password: %s", presentedPassword);
            throw new BadCredentialsException (message);
        }
    }

    private boolean verifyRefreshToken (String presentedRefreshToken, String email) {
        final JwtRefreshToken savedRefreshToken = refreshTokenService.findByEmail (email);
        final String token = savedRefreshToken.getToken ();

        return presentedRefreshToken.equals (token);
    }
}
