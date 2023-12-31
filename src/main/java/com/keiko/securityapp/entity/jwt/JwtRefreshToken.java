package com.keiko.securityapp.entity.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash ("JwtRefreshToken")
public class JwtRefreshToken implements Serializable {
    private String id;
    private String token;
}
