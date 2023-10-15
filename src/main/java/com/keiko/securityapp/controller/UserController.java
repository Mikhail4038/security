package com.keiko.securityapp.controller;

import com.keiko.securityapp.dto.model.user.UserDto;
import com.keiko.securityapp.entity.request.ModifyUserRolesRequest;
import com.keiko.securityapp.entity.security.User;
import com.keiko.securityapp.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/user")
public class UserController
        extends DefaultCrudController<User, UserDto> {

    @Autowired
    private UserService userService;

    @PostMapping ("/addRoles")
    public ResponseEntity addRoles (@RequestBody ModifyUserRolesRequest request) {
        userService.addRoles (request);
        return ResponseEntity.ok ().build ();
    }

    @PostMapping ("/removeRoles")
    public ResponseEntity removeUsers (@RequestBody ModifyUserRolesRequest request) {
        userService.removeRoles (request);
        return ResponseEntity.ok ().build ();
    }
}
