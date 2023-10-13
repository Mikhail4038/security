package com.keiko.securityapp.service.jwt.impl;

import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.entity.security.User;
import com.keiko.securityapp.service.jwt.JwtProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    private SecretKey accessKey;
    private SecretKey refreshKey;

    public DefaultJwtProvider (
            @Value ("${jwt.secret.access}") String accessKey,
            @Value ("${jwt.secret.refresh}") String refreshKey) {
        this.accessKey = Keys.hmacShaKeyFor (Decoders.BASE64.decode (accessKey));
        this.refreshKey = Keys.hmacShaKeyFor (Decoders.BASE64.decode (refreshKey));
    }

    @Override
    public String generateAccessToken (@NonNull User user) {
        final String email = user.getEmail ();

        final Set<String> roles = user.getRoles ()
                .stream ()
                .map (Role::getName)
                .collect (toSet ());

        final String name = user.getName ();
        final Instant accessExpirationInstant = now ().plusMinutes (15).atZone (systemDefault ()).toInstant ();
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
        final Instant accessExpirationInstant = now ().plusDays (30).atZone (systemDefault ()).toInstant ();
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

    @Override
    public Claims getAccessClaims (@NonNull String accessToken) {
        return getClaims (accessKey, accessToken);
    }

    @Override
    public Claims getRefreshClaims (@NonNull String refreshToken) {
        return getClaims (refreshKey, refreshToken);
    }

    private Claims getClaims (SecretKey secretKey, @NonNull String token) {
        return Jwts.parserBuilder ()
                .setSigningKey (secretKey).build ()
                .parseClaimsJws (token).getBody ();
    }
}
