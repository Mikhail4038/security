package com.keiko.securityapp.entity.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ModifyUserRolesRequest {
    private Long userId;
    private Set<Long> rolesId;
}
