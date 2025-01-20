package com.example.testSpringBoot.repository;

import com.example.testSpringBoot.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String  > {
}
