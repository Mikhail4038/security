package com.keiko.securityapp.dto.converter.role;

import com.keiko.securityapp.dto.model.RoleDto;
import com.keiko.securityapp.entity.Role;
import com.keiko.securityapp.entity.User;
import com.keiko.securityapp.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

@Component
public class DtoToRoleConverter implements Function<RoleDto, Role> {

    @Autowired
    private UserService userService;

    @Override
    public Role apply (RoleDto dto) {
        Role role = new Role ();
        role.setId (dto.getId ());
        role.setName (dto.getName ());

        if (Objects.nonNull (dto.getUsers ())) {
            Set<User> users = dto.getUsers ()
                    .stream ()
                    .map ((u) -> userService.findUserByEmail (u))
                    .collect (toSet ());
            role.setUsers (users);
        }
        return role;
    }
}
