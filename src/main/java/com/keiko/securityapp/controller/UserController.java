package com.keiko.securityapp.controller;

import com.keiko.securityapp.dto.model.UserDto;
import com.keiko.securityapp.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/user")
public class UserController extends DefaultCrudController<User, UserDto> {
}
