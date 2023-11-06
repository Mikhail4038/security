package com.keiko.securityapp.exception.model;

public class UserNotFoundException
        extends RuntimeException {

    public UserNotFoundException (String message) {
        super (message);
    }
}
