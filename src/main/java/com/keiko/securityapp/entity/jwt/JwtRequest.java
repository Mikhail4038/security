package com.keiko.securityapp.entity.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {

    // TODO not null?
    private String email;
    private String password;
}
