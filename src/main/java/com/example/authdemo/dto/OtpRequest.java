package com.example.authdemo.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpRequest {
    private String email;
    private String otp;
}
