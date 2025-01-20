package com.example.testSpringBoot.configuration;

import com.example.testSpringBoot.entity.User;
import com.example.testSpringBoot.enums.Role;
import com.example.testSpringBoot.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                var role = new HashSet<String>();
                role.add(Role.ADMIN.name());

                User user = User.builder().username("admin").password(passwordEncoder.encode("admin"))
//                        .roles(role)
                        .build();
                log.warn("Roles before save: {}", role);
                userRepository.save(user);
                log.warn("tạo tài khoản rồi");
            }
        };
    }
}
