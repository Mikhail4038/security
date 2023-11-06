package com.keiko.securityapp.repository.basic;

import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.repository.basic.AbstractCrudRepository;

import java.util.Optional;

public interface RoleRepository extends AbstractCrudRepository<Role> {

    Optional<Role> findByName (String name);
}
