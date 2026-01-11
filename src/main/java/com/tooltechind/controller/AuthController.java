package com.tooltechind.controller;

import com.tooltechind.dto.LoginRequest;
import com.tooltechind.entity.User;
import com.tooltechind.service.UserService;
import com.tooltechind.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, 
                         JwtUtil jwtUtil, 
                         PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.email())  // Fixed: .email()
            .orElse(null);  // Handle Optional
        
        if (user != null && 
            passwordEncoder.matches(loginRequest.password(), user.getPassword())) {  // Fixed: .password()
            
            // Fixed: user.getRole().name() for String
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            return ResponseEntity.ok(
                Map.of("token", token, "role", user.getRole().name())
            );
        }
        
        return ResponseEntity.status(401).build();
    }
    
    
    
//    @PostMapping("/auth/forgot-password")
//    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
//        String email = request.get("email");
//        String otp = otpService.generateOtp(email);  // 6-digit OTP
//        emailService.sendOtp(email, otp);  // SendGrid/Email
//        return ResponseEntity.ok(Map.of("message", "OTP sent to " + email));
//    }

//    @PostMapping("/auth/verify-otp")
//    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody Map<String, Object> request) {
//        String email = (String) request.get("email");
//        String otp = (String) request.get("otp");
//        if (otpService.validateOtp(email, otp)) {
//            return ResponseEntity.ok(Map.of("message", "OTP verified", "token", jwtUtil.generateResetToken(email)));
//        }
//        return ResponseEntity.badRequest().body(Map.of("error", "Invalid OTP"));
//    }
//
//    @PostMapping("/auth/reset-password")
//    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
//        String token = request.get("token");
//        String newPassword = request.get("password");
//        String email = jwtUtil.extractEmail(token);
//        userService.updatePassword(email, newPassword);
//        return ResponseEntity.ok(Map.of("message", "Password reset successful"));
//    }

}
