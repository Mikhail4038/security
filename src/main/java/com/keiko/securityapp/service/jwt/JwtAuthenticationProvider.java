package com.keiko.securityapp.service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtAuthenticationProvider {
    Authentication generate (Claims claims);
}
