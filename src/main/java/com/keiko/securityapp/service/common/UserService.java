package com.keiko.securityapp.service.common;

import com.keiko.securityapp.entity.User;

import java.util.Set;

public interface UserService {

    Set<User> findUserByRole (String role);

    User findUserByEmail (String email);
}
