package com.keiko.securityapp.service.jwt;

import com.keiko.securityapp.entity.jwt.JwtRefreshToken;
import lombok.NonNull;

public interface JwtRefreshTokenService {

    JwtRefreshToken findByEmail (String email);

    void save (@NonNull JwtRefreshToken jwtRefreshToken);

    void delete (String email);
}
