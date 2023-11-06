package com.keiko.securityapp.service.basic;

import com.keiko.securityapp.entity.security.Role;

public interface RoleService {
    Role findByName (String name);
}
