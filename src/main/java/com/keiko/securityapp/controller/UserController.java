package com.keiko.securityapp.controller;

import com.keiko.securityapp.dto.model.user.UserDto;
import com.keiko.securityapp.entity.request.ModifyUserRolesRequest;
import com.keiko.securityapp.entity.security.User;
import com.keiko.securityapp.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/user")
public class UserController
        extends DefaultCrudController<User, UserDto> {

    @Autowired
    private UserService userService;

    @GetMapping ("/byEmail")
    public ResponseEntity<UserDto> findByEmail (@RequestParam String email) {
        User user = userService.findUserByEmail (email);
        UserDto dto = getToDtoConverter ().apply (user);
        return ResponseEntity.ok ().body (dto);
    }

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
