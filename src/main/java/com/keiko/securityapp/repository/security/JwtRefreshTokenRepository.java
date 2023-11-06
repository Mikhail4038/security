package com.keiko.securityapp.repository.security;

import com.keiko.securityapp.entity.jwt.JwtRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface JwtRefreshTokenRepository
        extends CrudRepository<JwtRefreshToken, String> {
}
