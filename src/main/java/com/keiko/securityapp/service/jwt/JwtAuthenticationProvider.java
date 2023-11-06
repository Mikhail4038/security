package com.keiko.securityapp.service.jwt;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import org.springframework.security.core.Authentication;

public interface JwtAuthenticationProvider {
    Authentication generate (@NonNull Claims claims);
}
