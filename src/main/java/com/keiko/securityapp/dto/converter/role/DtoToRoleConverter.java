package com.keiko.securityapp.dto.converter.role;

import com.keiko.securityapp.dto.converter.AbstractToEntityConverter;
import com.keiko.securityapp.dto.model.role.RoleDto;
import com.keiko.securityapp.entity.security.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DtoToRoleConverter
        extends AbstractToEntityConverter<RoleDto, Role> {

    public DtoToRoleConverter () {
        super (RoleDto.class, Role.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (RoleDto.class, Role.class)
                .addMappings (mapper -> mapper.skip (Role::setUsers));
    }

    @Override
    public void mapSpecificFields (RoleDto dto, Role role) {
    }
}
