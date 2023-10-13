package com.keiko.securityapp.dto.converter.user;

import com.keiko.securityapp.dto.model.role.RoleData;
import com.keiko.securityapp.dto.model.user.UserDto;
import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.entity.security.User;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Component
public class DtoToUserConverter implements Function<UserDto, User> {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void setUpMapping () {
        modelMapper.createTypeMap (UserDto.class, User.class)
                .addMappings (mapper -> mapper.skip (User::setRoles))
                .setPostConverter (converter);
    }

    @Override
    public User apply (UserDto userDto) {
        return modelMapper.map (userDto, User.class);
    }

    private Converter<UserDto, User> converter = context -> {
        UserDto dto = context.getSource ();
        User user = context.getDestination ();
        mapSpecificFields (dto, user);
        return user;
    };

    private void mapSpecificFields (UserDto dto, User user) {
        Set<RoleData> rolesData = dto.getRoles ();
        if (nonNull (rolesData)) {
            Set<Role> roles = rolesData.stream ()
                    .map (d -> modelMapper.map (d, Role.class))
                    .collect (toSet ());
            user.setRoles (roles);
        }
    }
}
