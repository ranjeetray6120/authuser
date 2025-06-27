package com.example.authdemo.dto;


import lombok.*;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String fullName;
    private String email;
    private String role;
}
