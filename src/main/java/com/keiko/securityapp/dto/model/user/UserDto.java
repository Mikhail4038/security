package com.keiko.securityapp.dto.model.user;

import com.keiko.securityapp.dto.model.BaseDto;
import com.keiko.securityapp.dto.model.role.RoleData;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto extends BaseDto {
    private String email;
    private String password;
    private String name;
    private Set<RoleData> roles;
}
