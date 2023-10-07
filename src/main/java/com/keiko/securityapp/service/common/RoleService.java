package com.keiko.securityapp.service.common;

import com.keiko.securityapp.entity.Role;

public interface RoleService {
    Role findByName (String name);
}
