package com.keiko.securityapp.service.basic.impl;

import com.keiko.securityapp.entity.request.ModifyUserRolesRequest;
import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.entity.security.User;
import com.keiko.securityapp.exception.model.RoleNotFoundException;
import com.keiko.securityapp.exception.model.UserNotFoundException;
import com.keiko.securityapp.repository.basic.RoleRepository;
import com.keiko.securityapp.repository.basic.UserRepository;
import com.keiko.securityapp.service.basic.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.Collections.EMPTY_SET;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Service
public class DefaultUserService extends DefaultCrudService<User>
        implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void register (@NonNull User user) {
        final String presentedPassword = user.getPassword ();
        final String encodePassword = BCrypt.hashpw (presentedPassword, BCrypt.gensalt ());
        user.setPassword (encodePassword);
        userRepository.save (user);
    }

    @Override
    public User findByEmail (String email) {
        return userRepository.findUserByEmail (email)
                .orElseThrow (() -> new UserNotFoundException (
                        String.format ("User with email: %s not found", email)));
    }

    @Override
    public void deleteByEmail (String email) {
        userRepository.deleteByEmail (email);
    }

    @Override
    public void addRoles (@NonNull ModifyUserRolesRequest request) {
        Set<Role> rolesForAdd = getRolesFromRequest (request);

        if (isNotEmpty (rolesForAdd)) {
            User user = getUserFromRequest (request);
            Set<Role> presentedRoles = user.getRoles ();

            if (nonNull (presentedRoles)) {
                presentedRoles.addAll (rolesForAdd);
                user.setRoles (presentedRoles);
            } else {
                user.setRoles (rolesForAdd);
            }
            userRepository.save (user);
        }
    }

    @Override
    public void removeRoles (@NonNull ModifyUserRolesRequest request) {
        User user = getUserFromRequest (request);
        Set<Role> actualRoles = user.getRoles ();

        if (nonNull (actualRoles)) {
            Set<Role> rolesForRemove = getRolesFromRequest (request);
            rolesForRemove.forEach (actualRoles::remove);
            user.setRoles (actualRoles);
            userRepository.save (user);
        }
    }

    private User getUserFromRequest (ModifyUserRolesRequest request) {
        final Long userId = request.getUserId ();
        User user = userRepository.findById (userId)
                .orElseThrow (() -> new UserNotFoundException (
                        String.format ("User with id: %s not found", userId)));
        return user;
    }

    private Set<Role> getRolesFromRequest (ModifyUserRolesRequest request) {
        Set<Long> rolesName = request.getRolesId ();
        if (nonNull (rolesName)) {
            Set<Role> roles = rolesName.stream ()
                    .map (id -> roleRepository.findById (id)
                            .orElseThrow (() -> new RoleNotFoundException (String.format
                                    ("Role with id: %s not found", id))))
                    .collect (toSet ());
            return roles;
        }
        return EMPTY_SET;
    }
}
