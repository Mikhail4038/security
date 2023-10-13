package com.keiko.securityapp.service.common.impl;

import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.repository.common.RoleRepository;
import com.keiko.securityapp.service.common.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultRoleService extends DefaultCrudService<Role>
        implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName (String name) {
        return roleRepository.findByName (name).orElseThrow ();
    }
}
