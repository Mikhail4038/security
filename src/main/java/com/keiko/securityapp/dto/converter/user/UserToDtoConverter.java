package com.keiko.securityapp.dto.converter.user;

import com.keiko.securityapp.dto.model.UserDto;
import com.keiko.securityapp.entity.Role;
import com.keiko.securityapp.entity.User;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class UserToDtoConverter implements Function<User, UserDto> {

    @Override
    public UserDto apply (User user) {
        UserDto dto = new UserDto ();
        dto.setId (user.getId ());
        dto.setEmail (user.getEmail ());
        dto.setPassword (user.getPassword ());
        dto.setName (user.getName ());

        if (nonNull (user.getCreated ())) {
            dto.setCreated (user.getCreated ().toLocalDateTime ());
        }
        dto.setModified (user.getModified ().toLocalDateTime ());

        if (nonNull (user.getRoles ())) {
            Set<String> roles = user.getRoles ()
                    .stream ().map (Role::getName).collect (Collectors.toSet ());

            dto.setRoles (roles);
        }
        return dto;
    }
}
