//package com.tooltechind.service;
//
//import java.util.Random;
//
//import org.springframework.stereotype.Service;
//
//@Service
//@Component
//public class OtpService {
//    private final Map<String, String> otpStore = new ConcurrentHashMap<>();
//    private final RedisTemplate<String, String> redisTemplate;  // Or in-memory for dev
//    
//    public String generateOtp(String email) {
//        String otp = String.format("%06d", new Random().nextInt(1000000));
//        // redisTemplate.opsForValue().set(email, otp, Duration.ofMinutes(10));
//        otpStore.put(email, otp);  // Dev only
//        return otp;
//    }
//    
//    public boolean validateOtp(String email, String otp) {
//        String storedOtp = otpStore.get(email);
//        boolean valid = otp.equals(storedOtp);
//        if (valid) otpStore.remove(email);  // One-time use
//        return valid;
//    }
//}
