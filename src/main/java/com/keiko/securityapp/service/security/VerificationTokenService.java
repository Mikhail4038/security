package com.keiko.securityapp.service.security;

import com.keiko.securityapp.entity.security.VerificationToken;
import lombok.NonNull;

public interface VerificationTokenService {

    void createToken (@NonNull VerificationToken verificationToken);

    VerificationToken findByToken (String token);
}
