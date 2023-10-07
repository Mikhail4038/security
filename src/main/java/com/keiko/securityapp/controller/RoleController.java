package com.keiko.securityapp.controller;

import com.keiko.securityapp.dto.model.RoleDto;
import com.keiko.securityapp.entity.Role;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/role")
public class RoleController extends DefaultCrudController<Role, RoleDto> {
}
