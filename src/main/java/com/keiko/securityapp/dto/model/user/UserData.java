package com.keiko.securityapp.dto.model.user;

import com.keiko.securityapp.dto.model.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserData extends BaseDto {
    private String email;
    private String name;
    private boolean enabled;
}
