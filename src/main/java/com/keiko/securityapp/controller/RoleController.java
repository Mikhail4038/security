package com.keiko.securityapp.controller;

import com.keiko.securityapp.dto.model.role.RoleDto;
import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.service.basic.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/role")
public class RoleController
        extends DefaultCrudController<Role, RoleDto> {

    @Autowired
    private RoleService roleService;

    @GetMapping ("/byName")
    public ResponseEntity<RoleDto> findByName (@RequestParam String name) {
        Role role = roleService.findByName (name);
        RoleDto dto = getToDtoConverter ().apply (role);
        return ResponseEntity.ok ().body (dto);
    }

}
