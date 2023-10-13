package com.keiko.securityapp.dto.converter.user;

import com.keiko.securityapp.dto.converter.AbstractToEntityConverter;
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
public class DtoToUserConverter extends AbstractToEntityConverter<UserDto, User> {

    public DtoToUserConverter () {
        super (UserDto.class, User.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (UserDto.class, User.class)
                .addMappings (mapper -> mapper.skip (User::setRoles))
                .setPostConverter (converter);
    }

    public void mapSpecificFields (UserDto dto, User user) {
        Set<RoleData> rolesData = dto.getRoles ();
        if (nonNull (rolesData)) {
            Set<Role> roles = rolesData.stream ()
                    .map (d -> getModelMapper ().map (d, Role.class))
                    .collect (toSet ());
            user.setRoles (roles);
        }
    }
}
