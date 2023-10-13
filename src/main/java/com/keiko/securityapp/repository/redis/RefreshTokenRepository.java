package com.keiko.securityapp.repository.redis;

import com.keiko.securityapp.entity.redis.JwtRefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<JwtRefreshToken, String> {
}
