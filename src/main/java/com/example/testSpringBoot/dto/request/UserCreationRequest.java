package com.example.testSpringBoot.dto.request;

import com.example.testSpringBoot.validator.DobConstraint;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import lombok.*;

import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
     String username;
    @Size(min = 8, message = "PASSWORD_INVALID")
     String password;
     String email;
     @DobConstraint(min= 10, message = "INVALID_DOB")
     LocalDate dob;

}
