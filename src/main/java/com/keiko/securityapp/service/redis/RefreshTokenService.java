package com.keiko.securityapp.service.redis;

import com.keiko.securityapp.entity.redis.RefreshToken;

public interface RefreshTokenService {

    RefreshToken findByEmail (String email);

    RefreshToken save (RefreshToken refreshToken);
}
