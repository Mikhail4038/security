package com.keiko.securityapp.dto.converter.user;

import com.keiko.securityapp.dto.converter.AbstractToDtoConverter;
import com.keiko.securityapp.dto.model.role.RoleData;
import com.keiko.securityapp.dto.model.user.UserDto;
import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.entity.security.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Component
public class UserToDtoConverter
        extends AbstractToDtoConverter<User, UserDto> {

    public UserToDtoConverter () {
        super (User.class, UserDto.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (User.class, UserDto.class)
                .addMappings (mapper -> mapper.skip (UserDto::setPassword))
                .addMappings (mapper -> mapper.skip (UserDto::setRoles))
                .setPostConverter (converter);
    }

    public void mapSpecificFields (User user, UserDto dto) {
        Set<Role> roles = user.getRoles ();
        if (nonNull (roles)) {
            Set<RoleData> rolesData = roles.stream ()
                    .map (r -> getModelMapper ().map (r, RoleData.class))
                    .collect (toSet ());
            dto.setRoles (rolesData);
        }
    }
}
