package com.example.testSpringBoot.mapper;

import com.example.testSpringBoot.dto.request.PermissionRequest;
import com.example.testSpringBoot.dto.request.UserCreationRequest;
import com.example.testSpringBoot.dto.request.UserUpdateRequest;
import com.example.testSpringBoot.dto.response.PermissionResponse;
import com.example.testSpringBoot.dto.response.UserResponse;
import com.example.testSpringBoot.entity.Permission;
import com.example.testSpringBoot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
        Permission toPermission(PermissionRequest request);
        PermissionResponse toPermissionResponse(Permission permission);
}
