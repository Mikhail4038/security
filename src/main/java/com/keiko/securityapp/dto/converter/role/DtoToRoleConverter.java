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
public class DtoToRoleConverter implements Function<RoleDto, Role> {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void setUpMapping () {
        modelMapper.createTypeMap (RoleDto.class, Role.class)
                .addMappings (mapper -> mapper.skip (Role::setUsers))
                .setPostConverter (converter);
    }

    @Override
    public Role apply (RoleDto roleDto) {
        return modelMapper.map (roleDto, Role.class);
    }

    private Converter<RoleDto, Role> converter = context -> {
        RoleDto dto = context.getSource ();
        Role role = context.getDestination ();
        mapSpecificFields (dto, role);
        return null;
    };

    private void mapSpecificFields (RoleDto dto, Role role) {
        Set<UserData> usersData = dto.getUsers ();
        if (nonNull (usersData)) {
            Set<User> users = usersData.stream ()
                    .map (d -> modelMapper.map (d, User.class))
                    .collect (toSet ());
            role.setUsers (users);
        }
    }
}
