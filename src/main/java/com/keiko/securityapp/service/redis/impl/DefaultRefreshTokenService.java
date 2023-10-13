package com.keiko.securityapp.service.redis.impl;

import com.keiko.securityapp.entity.redis.JwtRefreshToken;
import com.keiko.securityapp.repository.redis.RefreshTokenRepository;
import com.keiko.securityapp.service.redis.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultRefreshTokenService implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public JwtRefreshToken findByEmail (String email) {
        JwtRefreshToken jwtRefreshToken = refreshTokenRepository.findById (email).orElseThrow ();
        return jwtRefreshToken;
    }

    @Override
    public JwtRefreshToken save (JwtRefreshToken jwtRefreshToken) {
        JwtRefreshToken savedJwtRefreshToken = refreshTokenRepository.save (jwtRefreshToken);
        return savedJwtRefreshToken;
    }

    @Override
    public void delete (String email) {
        refreshTokenRepository.deleteById (email);
    }
}
