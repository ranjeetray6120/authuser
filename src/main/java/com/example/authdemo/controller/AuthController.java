//package com.example.authdemo.controller;
//
//
//import com.example.authdemo.dto.*;
//import com.example.authdemo.model.User;
//import com.example.authdemo.repository.UserRepository;
//import com.example.authdemo.security.CustomUserDetailsService;
//import com.example.authdemo.security.JwtUtil;
//import com.example.authdemo.service.AuthService;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.*;
//import org.springframework.security.authentication.*;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthService authService;
//    private final JwtUtil jwtUtil;
//    private final AuthenticationManager authenticationManager;
//    private final CustomUserDetailsService userDetailsService;
//    private final UserRepository userRepository;
//
//    // ✉️ Step 1: Register and send OTP
//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
//        return ResponseEntity.ok(authService.register(request));
//    }
//
//    // ✅ Step 2: Verify OTP and save user
//    @PostMapping("/verify-otp")
//    public ResponseEntity<String> verifyOtp(@RequestBody OtpRequest request) {
//        return ResponseEntity.ok(authService.verifyOtp(request));
//    }
//
//    // 🔐 Step 3: Login & Get JWT Token
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        try {
//            // Authenticate using email + password
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequest.getEmail(), loginRequest.getPassword())
//            );
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//
//        // Load user and generate token
//        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
//        String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
//
//        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
//        return ResponseEntity.ok(
//                new LoginResponse(jwtToken, user.getFullName(), user.getEmail(), user.getUserRole())
//        );
//    }
//}
//
