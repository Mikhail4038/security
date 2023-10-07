package com.keiko.securityapp.dto.converter.user;

import com.keiko.securityapp.dto.model.UserDto;
import com.keiko.securityapp.entity.Role;
import com.keiko.securityapp.entity.User;
import com.keiko.securityapp.service.common.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class DtoToUserConverter implements Function<UserDto, User> {

    @Autowired
    private RoleService roleService;

    @Override
    public User apply (UserDto dto) {
        User user = new User ();
        user.setId (dto.getId ());
        user.setEmail (dto.getEmail ());
        user.setPassword (dto.getPassword ());
        user.setName (dto.getName ());

        if (nonNull (dto.getRoles ())) {
            Set<Role> roles = dto.getRoles ()
                    .stream ().map ((n) -> roleService.findByName (n)).collect (Collectors.toSet ());
            user.setRoles (roles);
        }
        return user;
    }
}
