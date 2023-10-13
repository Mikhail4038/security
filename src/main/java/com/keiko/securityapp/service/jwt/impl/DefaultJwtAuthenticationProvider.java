package com.keiko.securityapp.service.jwt.impl;

import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.entity.jwt.JwtAuthentication;
import com.keiko.securityapp.service.common.RoleService;
import com.keiko.securityapp.service.jwt.JwtAuthenticationProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultJwtAuthenticationProvider implements JwtAuthenticationProvider {


    @Autowired
    private RoleService roleService;

    @Override
    public Authentication generate (Claims claims) {
        JwtAuthentication jwtAuthentication = new JwtAuthentication ();
        final String email = claims.getSubject ();

        //TODO NPE?
        final String name = claims.get ("name").toString ();
        final Set<Role> roles = getUserRoles (claims);

        jwtAuthentication.setEmail (email);
        jwtAuthentication.setName (name);
        jwtAuthentication.setRoles (roles);

        return jwtAuthentication;
    }

    private Set<Role> getUserRoles (Claims claims) {
        List<String> names = claims.get ("roles", List.class);
        return names.stream ()
                .map ((n) -> roleService.findByName (n))
                .collect (Collectors.toSet ());
    }
}
