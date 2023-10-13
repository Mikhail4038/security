package com.keiko.securityapp.dto.model.role;

import com.keiko.securityapp.dto.model.BaseDto;
import com.keiko.securityapp.dto.model.user.UserData;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleDto extends BaseDto {
    private String name;
    private Set<UserData> users;
}
