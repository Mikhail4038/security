package com.keiko.securityapp.repository.security;

import com.keiko.securityapp.entity.security.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken (String token);
}
