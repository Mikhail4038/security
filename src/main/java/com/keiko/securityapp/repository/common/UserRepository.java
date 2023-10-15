package com.keiko.securityapp.repository.common;

import com.keiko.securityapp.entity.security.User;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface UserRepository extends AbstractCrudRepository<User> {

    Optional<User> findUserByEmail (String email);

    @Modifying
    void deleteByEmail (String email);
}
