package com.keiko.securityapp.repository.common;

import com.keiko.securityapp.entity.security.User;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends AbstractCrudRepository<User> {

    Set<User> findByRoles_nameLikeIgnoreCase (String role);

    Optional<User> findUserByEmail (String email);

    @Modifying
        //@Query("delete from ")
    void deleteByEmail (String email);
}
