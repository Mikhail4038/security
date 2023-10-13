package com.keiko.securityapp.service.jwt;

import com.keiko.securityapp.entity.jwt.JwtBlockUserRequest;
import com.keiko.securityapp.entity.jwt.JwtRefreshRequest;
import com.keiko.securityapp.entity.jwt.JwtRequest;
import com.keiko.securityapp.entity.jwt.JwtResponse;

public interface JwtTokenHelper {
    JwtResponse login (JwtRequest jwtRequest);

    JwtResponse getAccessToken (JwtRefreshRequest jwtRefreshRequest);

    JwtResponse getRefreshToken (JwtRefreshRequest jwtRefreshRequest);

    void blockUser (JwtBlockUserRequest jwtBlockUserRequest);
}
