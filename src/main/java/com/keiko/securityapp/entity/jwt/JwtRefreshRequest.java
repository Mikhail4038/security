package com.keiko.securityapp.entity.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRefreshRequest {

    private String refreshToken;
}
