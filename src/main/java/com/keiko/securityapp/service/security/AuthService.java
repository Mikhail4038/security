package com.keiko.securityapp.service.security;

import com.keiko.securityapp.entity.request.BlockUserRequest;
import com.keiko.securityapp.entity.request.LoginRequest;
import com.keiko.securityapp.entity.response.JwtResponse;
import com.keiko.securityapp.entity.security.User;
import lombok.NonNull;

public interface AuthService {
    void register (@NonNull User user);

    JwtResponse login (@NonNull LoginRequest loginRequest);

    void blockUser (@NonNull BlockUserRequest blockUserRequest);

    void confirmRegistration (String token);
}
