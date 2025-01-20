package com.example.testSpringBoot.dto.request;

import java.time.LocalDate;

import java.time.LocalDate;
import java.util.List;

import com.example.testSpringBoot.validator.DobConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Builder;
import lombok.experimental.FieldDefaults;


@Data
@Builder

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
     String password;
     String email;
     @DobConstraint(min= 18, message = "INVALID_DOB")
     LocalDate dob;
     List<String> roles;
}
