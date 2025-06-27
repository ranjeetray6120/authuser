package com.example.authdemo.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String companyName;

    @Column(unique = true, nullable = false)
    private String email;

    private String roleTitle;

    private String department;

    private String password;

    private boolean isVerified;

    private String userRole; // Default: "USER"
}

