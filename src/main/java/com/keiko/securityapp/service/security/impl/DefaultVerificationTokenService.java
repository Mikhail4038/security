package com.keiko.securityapp.service.security.impl;

import com.keiko.securityapp.entity.security.VerificationToken;
import com.keiko.securityapp.exception.model.VerificationTokenProcessException;
import com.keiko.securityapp.repository.security.VerificationTokenRepository;
import com.keiko.securityapp.service.security.VerificationTokenService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultVerificationTokenService
        implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    public void createToken (@NonNull VerificationToken verificationToken) {
        tokenRepository.save (verificationToken);
    }

    @Override
    public VerificationToken findByToken (String token) {
        return tokenRepository.findByToken (token)
                .orElseThrow (() -> new VerificationTokenProcessException (
                        String.format ("VerificationToken: %s not found", token)));
    }
}
