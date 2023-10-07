package com.keiko.securityapp.service.jwt;

import com.keiko.securityapp.entity.jwt.JwtRequest;
import com.keiko.securityapp.entity.jwt.JwtResponse;
import com.keiko.securityapp.entity.jwt.JwtRefreshRequest;

public interface JwtTokenHelper {
    JwtResponse login (JwtRequest jwtRequest);

    JwtResponse getAccessToken (JwtRefreshRequest jwtRefreshRequest);

    JwtResponse getRefreshToken (JwtRefreshRequest jwtRefreshRequest);
}
