package com.keiko.securityapp.service.redis;

import com.keiko.securityapp.entity.redis.JwtRefreshToken;

public interface RefreshTokenService {

    JwtRefreshToken findByEmail (String email);

    JwtRefreshToken save (JwtRefreshToken jwtRefreshToken);

    void delete (String email);
}
