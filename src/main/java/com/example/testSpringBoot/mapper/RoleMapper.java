package com.example.testSpringBoot.mapper;

import com.example.testSpringBoot.dto.request.RoleRequest;
import com.example.testSpringBoot.dto.response.RoleReponse;
import com.example.testSpringBoot.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleRequest request);

    @Mapping(source = "permissions", target = "permissions")
    RoleReponse toRoleResponse(Role role);
}
