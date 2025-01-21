package com.readnspeak.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.readnspeak.JwtUtil.JwtUtility;
import com.readnspeak.dto.ResponseDto;
import com.readnspeak.entity.Users;
import com.readnspeak.repository.UserRepository;
import com.readnspeak.service.UserService;

@RestController
public class AuthController {

    private final JwtUtility jwtUtility;
    private final UserService userService; // 사용자를 조회하는 서비스
    private final UserRepository userRepository;

    public AuthController(JwtUtility jwtUtility, UserService userService, UserRepository userRepository) {
        this.jwtUtility = jwtUtility;
        this.userService = userService;
		this.userRepository = userRepository;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@RequestBody String refreshToken) {
        // 클라이언트가 보낸 Refresh Token으로 사용자를 식별
        String username = jwtUtility.extractUsername(refreshToken);
        Users user = userRepository.findByUsername(username)
        		.orElseThrow(() -> new IllegalArgumentException("Invalid username"));
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        }
        
        try {
            // refreshAccessToken 메소드로 새로운 Access Token 발급
            String newAccessToken = jwtUtility.refreshAccessToken(refreshToken, user);
            return ResponseEntity.ok(newAccessToken);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }
    }
    
    
}
