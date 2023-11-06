package com.keiko.securityapp.service.jwt.impl;

import com.keiko.securityapp.entity.jwt.JwtRefreshToken;
import com.keiko.securityapp.entity.request.JwtRefreshRequest;
import com.keiko.securityapp.entity.response.JwtResponse;
import com.keiko.securityapp.entity.security.User;
import com.keiko.securityapp.service.basic.UserService;
import com.keiko.securityapp.service.jwt.JwtProvider;
import com.keiko.securityapp.service.jwt.JwtTokenHelper;
import com.keiko.securityapp.service.jwt.JwtRefreshTokenService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultJwtTokenHelper implements JwtTokenHelper {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtRefreshTokenService jwtRefreshTokenService;

    @Override
    public JwtResponse getAccessToken (@NonNull JwtRefreshRequest jwtRefreshRequest) {
        final String presentedRefreshToken = jwtRefreshRequest.getRefreshToken ();

        if (jwtProvider.validateRefreshToken (presentedRefreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims (presentedRefreshToken);
            final String email = claims.getSubject ();

            if (verifyRefreshToken (presentedRefreshToken, email)) {

                User user = userService.findByEmail (email);
                final String newAccessToken = jwtProvider.generateAccessToken (user);
                return new JwtResponse (newAccessToken, null);
            }
        }
        return new JwtResponse (null, null);
    }

    @Override
    public JwtResponse getRefreshToken (@NonNull JwtRefreshRequest jwtRefreshRequest) {
        final String presentedRefreshToken = jwtRefreshRequest.getRefreshToken ();

        if (jwtProvider.validateRefreshToken (presentedRefreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims (presentedRefreshToken);
            final String email = claims.getSubject ();

            if (verifyRefreshToken (presentedRefreshToken, email)) {

                final User user = userService.findByEmail (email);
                final String newRefreshToken = jwtProvider.generateRefreshToken (user);
                final String newAccessToken = jwtProvider.generateAccessToken (user);
                jwtRefreshTokenService.save (new JwtRefreshToken (email, newRefreshToken));

                return new JwtResponse (newAccessToken, newRefreshToken);
            }
        }
        return new JwtResponse (null, null);
    }

    private boolean verifyRefreshToken (String presentedRefreshToken, String email) {
        final JwtRefreshToken savedRefreshToken = jwtRefreshTokenService.findByEmail (email);
        final String token = savedRefreshToken.getToken ();

        return presentedRefreshToken.equals (token);
    }
}
