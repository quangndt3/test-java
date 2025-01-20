package com.example.testSpringBoot.mapper;

import com.example.testSpringBoot.dto.request.UserCreationRequest;
import com.example.testSpringBoot.dto.request.UserUpdateRequest;
import com.example.testSpringBoot.dto.response.UserResponse;
import com.example.testSpringBoot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
        User toUser(UserCreationRequest request);
        UserResponse toUserResponse(User user);

        @Mapping(target = "roles", ignore = true)
        void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
