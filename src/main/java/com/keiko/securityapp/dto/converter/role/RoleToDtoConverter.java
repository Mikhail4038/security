package com.keiko.securityapp.dto.converter.role;

import com.keiko.securityapp.dto.model.role.RoleDto;
import com.keiko.securityapp.dto.model.user.UserData;
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
public class RoleToDtoConverter implements Function<Role, RoleDto> {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void setUpMapping () {
        modelMapper.createTypeMap (Role.class, RoleDto.class);
    }

    // TODO abstract
    @Override
    public RoleDto apply (Role role) {
        return modelMapper.map (role, RoleDto.class);
    }

    // TODO abstract
    private Converter<Role, RoleDto> converter = context -> {
        Role role = context.getSource ();
        RoleDto dto = context.getDestination ();
        mapSpecificFields (role, dto);
        return dto;
    };

    private void mapSpecificFields (Role role, RoleDto dto) {
        Set<User> users = role.getUsers ();
        if (nonNull (users)) {
            Set<UserData> usersData = users.stream ()
                    .map (u -> modelMapper.map (u, UserData.class))
                    .collect (toSet ());
            dto.setUsers (usersData);
        }
    }
}
