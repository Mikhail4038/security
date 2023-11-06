package com.keiko.securityapp.repository.basic;

import com.keiko.securityapp.entity.security.User;
import com.keiko.securityapp.repository.basic.AbstractCrudRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface UserRepository extends AbstractCrudRepository<User> {

    Optional<User> findUserByEmail (String email);

    @Modifying
    void deleteByEmail (String email);
}
