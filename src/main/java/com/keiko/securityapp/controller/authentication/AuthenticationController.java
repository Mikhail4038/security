package com.keiko.securityapp.controller.authentication;

import com.keiko.securityapp.entity.jwt.JwtRefreshRequest;
import com.keiko.securityapp.entity.jwt.JwtRequest;
import com.keiko.securityapp.entity.jwt.JwtResponse;
import com.keiko.securityapp.service.jwt.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/auth")
public class AuthenticationController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @PostMapping ("/login")
    public ResponseEntity<JwtResponse> login (@RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = jwtTokenHelper.login (jwtRequest);
        return ResponseEntity.status (HttpStatus.OK).body (jwtResponse);
    }

    @PostMapping ("/accessToken")
    public ResponseEntity<JwtResponse> getNewAccessToken (@RequestBody JwtRefreshRequest jwtRefreshRequest) {
        JwtResponse jwtResponse = jwtTokenHelper.getAccessToken (jwtRefreshRequest);
        return ResponseEntity.ok ().body (jwtResponse);
    }

    @PostMapping ("/refreshToken")
    public ResponseEntity<JwtResponse> getNewRefreshToken (@RequestBody JwtRefreshRequest jwtRefreshRequest) {
        JwtResponse jwtResponse = jwtTokenHelper.getRefreshToken (jwtRefreshRequest);
        return ResponseEntity.ok ().body (jwtResponse);
    }
}
