package com.keiko.securityapp.repository.common;

import com.keiko.securityapp.entity.Role;
import com.keiko.securityapp.repository.common.AbstractCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends AbstractCrudRepository<Role> {

    Optional<Role> findByName (String name);
}
