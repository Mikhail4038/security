package com.keiko.securityapp.dto.converter.role;

import com.keiko.securityapp.dto.model.RoleDto;
import com.keiko.securityapp.entity.Role;
import com.keiko.securityapp.entity.User;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Component
public class RoleToDtoConverter implements Function<Role, RoleDto> {

    @Override
    public RoleDto apply (Role role) {
        RoleDto dto = new RoleDto ();
        dto.setId (role.getId ());
        dto.setName (role.getName ());
        if (role.getCreated () != null) {
            dto.setCreated (role.getCreated ().toLocalDateTime ());
        }
        dto.setModified (role.getModified ().toLocalDateTime ());

        if (nonNull (role.getUsers ())) {
            Set<String> users = role.getUsers ()
                    .stream ().map (User::getEmail).collect (toSet ());

            dto.setUsers (users);
        }
        return dto;
    }
}
