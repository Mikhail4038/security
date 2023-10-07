package com.keiko.securityapp.filter;

import com.keiko.securityapp.service.jwt.JwtAuthenticationProvider;
import com.keiko.securityapp.service.jwt.JwtTokenHelper;
import com.keiko.securityapp.service.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Component
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public void doFilter (ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        final String accessToken = getTokenFromRequest ((HttpServletRequest) request);

        if (nonNull (accessToken)
                && jwtProvider.validateAccessToken (accessToken)) {

            final Claims claims = jwtProvider.getAccessClaims (accessToken);
            Authentication authentication = jwtAuthenticationProvider.generate (claims);
            authentication.setAuthenticated (true);
            SecurityContextHolder.getContext ().setAuthentication (authentication);
        }
        filterChain.doFilter (request, response);
    }

    private String getTokenFromRequest (HttpServletRequest request) {
        final String bearer = request.getHeader (AUTHORIZATION);
        if (StringUtils.isNotBlank (bearer)
                && bearer.startsWith ("Bearer ")) {
            return bearer.substring (7);
        }
        return null;
    }
}
