package com.keiko.securityapp.dto.converter.user;

import com.keiko.securityapp.dto.model.role.RoleData;
import com.keiko.securityapp.dto.model.user.UserDto;
import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.entity.security.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Component
public class UserToDtoConverter implements Function<User, UserDto> {

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public void setUpMapping () {
        modelMapper.createTypeMap (User.class, UserDto.class)
                .addMappings (mapper -> mapper.skip (UserDto::setPassword))
                .addMappings (mapper -> mapper.skip (UserDto::setRoles))
                .setPostConverter (converter);
    }

    @Override
    public UserDto apply (User user) {
        return modelMapper.map (user, UserDto.class);
    }

    private Converter<User, UserDto> converter = context -> {
        User user = context.getSource ();
        UserDto dto = context.getDestination ();
        mapSpecificFields (user, dto);
        return dto;
    };

    private void mapSpecificFields (User user, UserDto dto) {
        Set<Role> roles = user.getRoles ();
        if (nonNull (roles)) {
            Set<RoleData> rolesData = roles.stream ()
                    .map (r -> modelMapper.map (r, RoleData.class))
                    .collect (toSet ());
            dto.setRoles (rolesData);
        }
    }
}
