package com.example.authdemo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.authdemo.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check if email already exists (for validation)
    boolean existsByEmail(String email);
}
