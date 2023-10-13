package com.keiko.securityapp.repository.common;

import com.keiko.securityapp.entity.security.Role;

import java.util.Optional;

public interface RoleRepository extends AbstractCrudRepository<Role> {

    Optional<Role> findByName (String name);
}
