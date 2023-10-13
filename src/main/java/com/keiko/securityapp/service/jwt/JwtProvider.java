package com.keiko.securityapp.service.jwt;

import com.keiko.securityapp.entity.security.User;
import io.jsonwebtoken.Claims;
import lombok.NonNull;

/**
 * Generate and validate jwt token (access, refresh)
 */
public interface JwtProvider {
    String generateAccessToken (@NonNull User user);

    String generateRefreshToken (@NonNull User user);

    boolean validateAccessToken (@NonNull String accessToken);

    boolean validateRefreshToken (@NonNull String refreshToken);

    Claims getAccessClaims (@NonNull String accessToken);

    Claims getRefreshClaims (@NonNull String refreshToken);
}
