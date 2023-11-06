package com.keiko.securityapp.exception.model;

public class RoleNotFoundException
        extends RuntimeException {

    public RoleNotFoundException (String message) {
        super (message);
    }
}
