package com.example.testSpringBoot.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.example.testSpringBoot.dto.request.UserCreationRequest;
import com.example.testSpringBoot.dto.request.UserUpdateRequest;
import com.example.testSpringBoot.dto.response.UserResponse;
import com.example.testSpringBoot.entity.User;
import com.example.testSpringBoot.enums.Role;
import com.example.testSpringBoot.exception.AppException;
import com.example.testSpringBoot.exception.ErrorCode;
import com.example.testSpringBoot.mapper.UserMapper;
import com.example.testSpringBoot.repository.RoleRepository;
import com.example.testSpringBoot.repository.UserRepository;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
     UserRepository userRepository;
     UserMapper userMapper;
     RoleRepository roleRepository;
    public User createUser(UserCreationRequest request){

        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
//        user.setRoles(roles);
        return userRepository.save(user);
    }
    public UserResponse getMyInfor(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User byUsername = userRepository.findByUsername(name).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(byUsername);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(){
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String id){
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(String id, UserUpdateRequest request){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userMapper.updateUser(user, request);
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public void deleteUser (String id){
         userRepository.deleteById(id);
    }
}
