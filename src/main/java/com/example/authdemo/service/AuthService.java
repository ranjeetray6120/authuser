package com.example.authdemo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authdemo.dto.OtpRequest;
import com.example.authdemo.dto.SignupRequest;
import com.example.authdemo.model.User;
import com.example.authdemo.repository.UserRepository;

@Service
public class AuthService {

    private final Map<String, SignupRequest> pendingUsers = new HashMap<>();
    private final Map<String, String> emailOtpMap = new HashMap<>();

    // Inject userRepository, passwordEncoder, emailService, etc.
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;


    public String register(SignupRequest request) {
        // 🔐 Step 1: Check if user already exists in DB
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "❌ Email already registered. Please login instead.";
        }

        // 🔐 Step 2: Check if already in pendingUsers (OTP not verified yet)
        if (pendingUsers.containsKey(request.getEmail())) {
            return "⚠️ OTP already sent to this email. Please check your inbox.";
        }

        // ✅ Step 3: Generate and store OTP
        String otp = generateOtp();
        emailOtpMap.put(request.getEmail(), otp);
        pendingUsers.put(request.getEmail(), request);

        // ✅ Step 4: Send email
        emailService.sendOtp(request.getEmail(), otp);

        return "✅ OTP sent to email.";
    }

    public String verifyOtp(OtpRequest otpRequest) {
    	
    	System.out.println("📩 Email received: " + otpRequest.getEmail());
        System.out.println("🔑 OTP received: " + otpRequest.getOtp());

        String expectedOtp = emailOtpMap.get(otpRequest.getEmail());
        System.out.println("✅ Expected OTP: " + expectedOtp);

        if (expectedOtp != null && expectedOtp.equals(otpRequest.getOtp())) {
            SignupRequest req = pendingUsers.get(otpRequest.getEmail());

            User user = User.builder()
                    .fullName(req.getFullName())
                    .companyName(req.getCompanyName())
                    .email(req.getEmail())
                    .roleTitle(req.getRoleTitle())
                    .department(req.getDepartment())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .isVerified(true)
                    .userRole("USER")
                    .build();

            userRepository.save(user);

            // Cleanup
            pendingUsers.remove(otpRequest.getEmail());
            emailOtpMap.remove(otpRequest.getEmail());

            return "User verified and saved successfully";
        }

        return "Invalid OTP";
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit OTP
    }
}
