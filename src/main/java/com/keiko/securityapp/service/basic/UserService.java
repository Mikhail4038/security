package com.keiko.securityapp.service.basic;

import com.keiko.securityapp.entity.request.ModifyUserRolesRequest;
import com.keiko.securityapp.entity.security.User;
import lombok.NonNull;

public interface UserService {
    void register (@NonNull User user);

    User findByEmail (String email);

    void deleteByEmail (String email);

    void addRoles (@NonNull ModifyUserRolesRequest request);

    void removeRoles (@NonNull ModifyUserRolesRequest request);
}
