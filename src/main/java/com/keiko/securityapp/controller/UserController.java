package com.keiko.securityapp.controller;

import com.keiko.securityapp.dto.model.user.UserDto;
import com.keiko.securityapp.entity.security.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/user")
public class UserController extends DefaultCrudController<User, UserDto> {
}
