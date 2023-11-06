package com.keiko.securityapp.service.basic.impl;

import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.exception.model.RoleNotFoundException;
import com.keiko.securityapp.repository.basic.RoleRepository;
import com.keiko.securityapp.service.basic.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultRoleService extends DefaultCrudService<Role>
        implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName (String name) {
        return roleRepository.findByName (name)
                .orElseThrow (() -> new RoleNotFoundException (
                        String.format ("Role with name: %s not found", name)));
    }
}
