package com.keiko.securityapp.service.redis.impl;

import com.keiko.securityapp.entity.redis.RefreshToken;
import com.keiko.securityapp.repository.redis.RefreshTokenRepository;
import com.keiko.securityapp.service.redis.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultRefreshTokenService implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken findByEmail (String email) {
        RefreshToken refreshToken = refreshTokenRepository.findById (email).orElseThrow ();
        return refreshToken;
    }

    @Override
    public RefreshToken save (RefreshToken refreshToken) {
        RefreshToken savedRefreshToken = refreshTokenRepository.save (refreshToken);
        return savedRefreshToken;
    }
}
