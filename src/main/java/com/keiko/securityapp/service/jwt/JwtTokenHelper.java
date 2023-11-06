package com.keiko.securityapp.service.jwt;

import com.keiko.securityapp.entity.request.JwtRefreshRequest;
import com.keiko.securityapp.entity.response.JwtResponse;
import lombok.NonNull;

public interface JwtTokenHelper {
    JwtResponse getAccessToken (@NonNull JwtRefreshRequest jwtRefreshRequest);

    JwtResponse getRefreshToken (@NonNull JwtRefreshRequest jwtRefreshRequest);
}
