package com.example.testSpringBoot.controller;


import com.example.testSpringBoot.dto.request.ApiResponse;
import com.example.testSpringBoot.dto.request.RoleRequest;
import com.example.testSpringBoot.dto.response.RoleReponse;
import com.example.testSpringBoot.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleReponse> create(@RequestBody RoleRequest request) {
            return  ApiResponse.<RoleReponse>builder()
                    .result(roleService.create(request))
                    .build();
    }

    @GetMapping
    ApiResponse<List<RoleReponse>> getAll(){
        return ApiResponse.<List<RoleReponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String role){
        roleService.delete(role);
        return ApiResponse.<Void>builder().build();
    }
}
