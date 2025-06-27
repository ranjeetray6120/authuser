package com.example.authdemo.controller;

import com.example.authdemo.dto.*;
import com.example.authdemo.model.User;
import com.example.authdemo.repository.UserRepository;
import com.example.authdemo.security.CustomUserDetailsService;
import com.example.authdemo.security.JwtUtil;
import com.example.authdemo.service.AuthService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthUserController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    // Render signup form
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "signup";
    }

    // Handle form submission for signup
    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupRequest request, Model model, HttpSession session) {
        String result = authService.register(request); // ⚠️ May return error OR success message

        // ❌ If email already registered or OTP already sent
        if (!result.startsWith("✅")) {
            model.addAttribute("error", result);  // Show message on signup page
            model.addAttribute("signupRequest", request); // refill form data
            return "signup"; // ✅ go back to signup form
        }

        // ✅ OTP sent successfully — go to verify-otp
        session.setAttribute("signupEmail", request.getEmail());
        OtpRequest otpRequest = new OtpRequest();
        otpRequest.setEmail(request.getEmail());

        model.addAttribute("otpRequest", otpRequest);
        return "verify-otp";
    }



    // Render OTP verification page
    @GetMapping("/verify-otp")
    public String showOtpPage(Model model, HttpSession session) {
        String email = (String) session.getAttribute("signupEmail");

        if (email == null) {
            System.out.println("❌ Email not found in session");
            return "redirect:/auth/signup";
        }

        OtpRequest otpRequest = new OtpRequest();
        otpRequest.setEmail(email); // ✅ Set the email inside the object

        model.addAttribute("otpRequest", otpRequest);
        return "verify-otp";
    }



    // Handle OTP verification
    @PostMapping("/verify-otp")
    public String verifyOtp(@ModelAttribute OtpRequest otpRequest,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        String result = authService.verifyOtp(otpRequest);

        if (result.equalsIgnoreCase("User verified and saved successfully")) {
            redirectAttributes.addFlashAttribute("success", "OTP verified successfully! Please log in.");
            return "redirect:/auth/login";
        }

        model.addAttribute("message", result);
        model.addAttribute("otpRequest", otpRequest);
        return "verify-otp";
    }

    // Render login form
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    // Handle login form submission
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, Model model, HttpSession session) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        session.setAttribute("user", user);
        session.setAttribute("token", token);
        model.addAttribute("fullName", user.getFullName());

        return "success";
    }

    // Logout endpoint
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
