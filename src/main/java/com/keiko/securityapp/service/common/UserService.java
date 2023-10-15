package com.keiko.securityapp.service.common;

import com.keiko.securityapp.entity.request.ModifyUserRolesRequest;
import com.keiko.securityapp.entity.security.User;
import lombok.NonNull;

public interface UserService {
    User findUserByEmail (String email);

    void deleteByEmail (String email);

    void addRoles (@NonNull ModifyUserRolesRequest request);

    void removeRoles (@NonNull ModifyUserRolesRequest request);
}
