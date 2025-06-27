package com.example.authdemo.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
    private String fullName;
    private String companyName;
    private String email;
    private String roleTitle;
    private String department;
    private String password;
    private String confirmPassword;
}
