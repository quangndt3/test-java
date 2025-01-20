package com.example.testSpringBoot.service;

import com.example.testSpringBoot.dto.request.RoleRequest;
import com.example.testSpringBoot.dto.response.RoleReponse;
import com.example.testSpringBoot.mapper.RoleMapper;
import com.example.testSpringBoot.repository.PermissionRepository;
import com.example.testSpringBoot.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleReponse create(RoleRequest request) {
            var role = roleMapper.toRole(request);
            var permissions = permissionRepository.findAllById(request.getPermissions());
            role.setPermissions(new HashSet<>(permissions));
            log.info("Role created: {}", role);
            role = roleRepository.save(role);
            return roleMapper.toRoleResponse(role);
    }
    public List<RoleReponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();

    }
    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
