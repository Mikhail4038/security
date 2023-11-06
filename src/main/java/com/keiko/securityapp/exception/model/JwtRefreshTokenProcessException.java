package com.keiko.securityapp.exception.model;

public class JwtRefreshTokenProcessException
        extends RuntimeException {

    public JwtRefreshTokenProcessException (String message) {
        super (message);
    }
}
