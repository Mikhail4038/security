package com.keiko.securityapp.dto.converter.role;

import com.keiko.securityapp.dto.converter.AbstractToDtoConverter;
import com.keiko.securityapp.dto.model.role.RoleDto;
import com.keiko.securityapp.dto.model.user.UserData;
import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.entity.security.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Component
public class RoleToDtoConverter
        extends AbstractToDtoConverter<Role, RoleDto> {

    public RoleToDtoConverter () {
        super (Role.class, RoleDto.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (Role.class, RoleDto.class)
                .addMappings (mapper -> mapper.skip (RoleDto::setUsers))
                .setPostConverter (converter);
        getModelMapper ().createTypeMap (User.class, UserData.class)
                .addMappings (mapper -> mapper.skip (UserData::setPassword));
    }

    public void mapSpecificFields (Role role, RoleDto dto) {
        Set<User> users = role.getUsers ();
        if (nonNull (users)) {
            Set<UserData> usersData = users.stream ()
                    .map (u -> getModelMapper ().map (u, UserData.class))
                    .collect (toSet ());
            dto.setUsers (usersData);
        }
    }
}
