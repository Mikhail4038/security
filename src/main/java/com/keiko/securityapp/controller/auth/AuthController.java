package com.keiko.securityapp.controller.auth;

import com.keiko.securityapp.dto.model.user.UserDto;
import com.keiko.securityapp.entity.request.BlockUserRequest;
import com.keiko.securityapp.entity.request.JwtRefreshRequest;
import com.keiko.securityapp.entity.request.LoginRequest;
import com.keiko.securityapp.entity.response.JwtResponse;
import com.keiko.securityapp.entity.security.User;
import com.keiko.securityapp.event.OnRegistrationCompleteEvent;
import com.keiko.securityapp.service.jwt.JwtTokenHelper;
import com.keiko.securityapp.service.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

import static com.keiko.securityapp.constants.WebResourceKeyConstants.*;

@RestController
@RequestMapping (value = AUTH_API)
public class AuthController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private AuthService authService;

    @Autowired
    private Function<UserDto, User> toUserConverter;

    @PostMapping (value = REGISTRATION_USER)
    public ResponseEntity registration (@RequestBody UserDto dto) {
        User user = toUserConverter.apply (dto);
        authService.register (user);
        eventPublisher.publishEvent (new OnRegistrationCompleteEvent (user));
        return ResponseEntity.ok ().build ();
    }

    @GetMapping (value = CONFIRM_REGISTRATION)
    public ResponseEntity confirmRegistration (@RequestParam String token) {
        authService.confirmRegistration (token);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = LOGIN_USER)
    public ResponseEntity<JwtResponse> login (@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login (loginRequest);
        return ResponseEntity.status (HttpStatus.OK).body (jwtResponse);
    }

    @PostMapping (value = BLOCK_USER)
    public ResponseEntity block (@RequestBody BlockUserRequest blockUserRequest) {
        authService.blockUser (blockUserRequest);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping (value = GENERATE_NEW_ACCESS_TOKEN)
    public ResponseEntity<JwtResponse> getNewAccessToken (@RequestBody JwtRefreshRequest jwtRefreshRequest) {
        JwtResponse jwtResponse = jwtTokenHelper.getAccessToken (jwtRefreshRequest);
        return ResponseEntity.ok ().body (jwtResponse);
    }

    @PostMapping (value = GENERATE_NEW_REFRESH_TOKEN)
    public ResponseEntity<JwtResponse> getNewRefreshToken (@RequestBody JwtRefreshRequest jwtRefreshRequest) {
        JwtResponse jwtResponse = jwtTokenHelper.getRefreshToken (jwtRefreshRequest);
        return ResponseEntity.ok ().body (jwtResponse);
    }
}
