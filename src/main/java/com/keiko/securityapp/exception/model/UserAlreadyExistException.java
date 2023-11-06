package com.keiko.securityapp.exception.model;

public class UserAlreadyExistException
        extends RuntimeException {

    public UserAlreadyExistException (String message) {
        super (message);
    }
}
