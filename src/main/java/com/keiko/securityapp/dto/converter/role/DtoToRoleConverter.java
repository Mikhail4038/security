package com.keiko.securityapp.dto.converter.role;

import com.keiko.securityapp.dto.converter.AbstractToEntityConverter;
import com.keiko.securityapp.dto.model.role.RoleDto;
import com.keiko.securityapp.dto.model.user.UserData;
import com.keiko.securityapp.entity.security.Role;
import com.keiko.securityapp.entity.security.User;
import com.keiko.securityapp.service.common.impl.DefaultCrudService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Component
public class DtoToRoleConverter
        extends AbstractToEntityConverter<RoleDto, Role> {

    @Autowired
    private DefaultCrudService<User> userService;

    public DtoToRoleConverter () {
        super (RoleDto.class, Role.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (RoleDto.class, Role.class)
                .addMappings (mapper -> mapper.skip (Role::setUsers))
                .setPostConverter (converter);
    }

    @Override
    public void mapSpecificFields (RoleDto dto, Role role) {
        Set<UserData> usersData = dto.getUsers ();
        if (nonNull (usersData)) {
            Set<User> users = usersData.stream ()
                    .map (d -> getModelMapper ().map (d, User.class))
                    .collect (toSet ());
            Set<Role> roles = new HashSet<> ();
            roles.add (role);
            users
                    .stream ()
                    .peek (u -> u.setRoles (roles))
                    .forEach (u -> userService.save (u));
        }
    }
}
