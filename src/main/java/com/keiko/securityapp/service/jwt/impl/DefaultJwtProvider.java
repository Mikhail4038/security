package com.keiko.securityapp.service.jwt.impl;

import com.keiko.securityapp.configuration.JwtProperties;
import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.entity.security.User;
import com.keiko.securityapp.service.jwt.JwtProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
public class DefaultJwtProvider implements JwtProvider {

    @Autowired
    private JwtProperties jwtProperties;

    private SecretKey accessKey;
    private SecretKey refreshKey;

    @PostConstruct
    public void init () {
        String jwtSecretAccess = jwtProperties.getAccessSecret ();
        String jwtSecretRefresh = jwtProperties.getRefreshSecret ();

        this.accessKey = Keys.hmacShaKeyFor (Decoders.BASE64.decode (jwtSecretAccess));
        this.refreshKey = Keys.hmacShaKeyFor (Decoders.BASE64.decode (jwtSecretRefresh));
    }

    @Override
    public String generateAccessToken (@NonNull User user) {
        final String email = user.getEmail ();


        // TODO put only email, without roles?
        final Set<String> roles = user.getRoles ()
                .stream ()
                .map (Role::getName)
                .collect (toSet ());

        final String name = user.getName ();
        final Long validityPeriod = jwtProperties.getValidityPeriodRefreshToken ();
        final Instant accessExpirationInstant = now ().plusMinutes (validityPeriod).atZone (systemDefault ()).toInstant ();
        final Date accessExpiration = Date.from (accessExpirationInstant);

        return Jwts.builder ()
                .signWith (accessKey)
                .setExpiration (accessExpiration)
                .setSubject (email)
                .claim ("name", name)
                .claim ("roles", roles)
                .compact ();
    }

    @Override
    public String generateRefreshToken (@NonNull User user) {
        final String email = user.getEmail ();
        final Long validityPeriod = jwtProperties.getValidityPeriodRefreshToken ();
        final Instant accessExpirationInstant = now ().plusDays (validityPeriod).atZone (systemDefault ()).toInstant ();
        final Date accessExpiration = Date.from (accessExpirationInstant);

        return Jwts.builder ()
                .signWith (refreshKey)
                .setExpiration (accessExpiration)
                .setSubject (email)
                .compact ();
    }

    @Override
    public boolean validateAccessToken (@NonNull String accessToken) {
        return validateToken (accessKey, accessToken);
    }

    @Override
    public boolean validateRefreshToken (@NonNull String refreshToken) {
        return validateToken (refreshKey, refreshToken);
    }

    @Override
    public Claims getAccessClaims (@NonNull String accessToken) {
        return getClaims (accessKey, accessToken);
    }

    @Override
    public Claims getRefreshClaims (@NonNull String refreshToken) {
        return getClaims (refreshKey, refreshToken);
    }

    private boolean validateToken (SecretKey secretKey, String token) {

        try {
            Jwts.parserBuilder ()
                    .setSigningKey (secretKey).build ()
                    .parseClaimsJws (token);
            return true;

        } catch (ExpiredJwtException expEx) {
            log.error ("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error ("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error ("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error ("Invalid signature", sEx);
        } catch (Exception e) {
            log.error ("invalid token", e);
        }
        return false;
    }

    private Claims getClaims (SecretKey secretKey, @NonNull String token) {
        return Jwts.parserBuilder ()
                .setSigningKey (secretKey).build ()
                .parseClaimsJws (token).getBody ();
    }
}
