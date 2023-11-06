package com.keiko.securityapp.service.jwt.impl;

import com.keiko.securityapp.entity.jwt.JwtRefreshToken;
import com.keiko.securityapp.exception.model.JwtRefreshTokenProcessException;
import com.keiko.securityapp.repository.security.JwtRefreshTokenRepository;
import com.keiko.securityapp.service.jwt.JwtRefreshTokenService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultJwtRefreshTokenService implements JwtRefreshTokenService {

    @Autowired
    private JwtRefreshTokenRepository jwtRefreshTokenRepository;

    @Override
    public JwtRefreshToken findByEmail (String email) {
        JwtRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.findById (email)
                .orElseThrow (() -> new JwtRefreshTokenProcessException (String.format
                        ("Token with email: %s not found", email)));
        return jwtRefreshToken;
    }

    @Override
    public void save (@NonNull JwtRefreshToken jwtRefreshToken) {
        jwtRefreshTokenRepository.save (jwtRefreshToken);
    }

    @Override
    public void delete (String email) {
        jwtRefreshTokenRepository.deleteById (email);
    }
}
