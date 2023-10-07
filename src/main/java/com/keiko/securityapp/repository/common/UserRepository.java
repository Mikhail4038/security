package com.keiko.securityapp.repository.common;

import com.keiko.securityapp.entity.User;
import com.keiko.securityapp.repository.common.AbstractCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends AbstractCrudRepository<User> {

    Set<User> findByRoles_nameLikeIgnoreCase (String role);

    Optional<User> findUserByEmail (String email);
}
