package com.readnspeak.controller;

import com.readnspeak.JwtUtil.JwtUtility;
import com.readnspeak.dto.ResponseDto;
import com.readnspeak.entity.EmailVerificationRequest;
import com.readnspeak.entity.EmailVerificationToken;
import com.readnspeak.entity.Users;
import com.readnspeak.repository.UserRepository;
import com.readnspeak.service.EmailVerificationService;
import com.readnspeak.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	private final EmailVerificationService emailVerificationService;
	
	@Autowired
    public UserController(UserService userService, EmailVerificationService emailVerificationService) {
        this.userService = userService;
        this.emailVerificationService = emailVerificationService;
    }

    
 // Register endpoint
	@PostMapping("/verify-email")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        try {
            // 1. 이메일 인증 코드 보내기
            emailVerificationService.sendVerificationEmail(user.getEmail());

            return ResponseEntity.ok("Verification email sent, please verify your email to complete registration.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
	
	@PostMapping("/register")
    public ResponseEntity<?> verifyEmail(@RequestBody EmailVerificationRequest emailVerificationRequest) {
        try {
            // 2. 이메일 인증 코드 검증
            boolean isValid = emailVerificationService.verifyToken(emailVerificationRequest.getEmail(), emailVerificationRequest.getToken());
            if (isValid) {
                // 3. 사용자 등록
                Users newUser = userService.registerUser(emailVerificationRequest.getUser());
                return ResponseEntity.ok(newUser);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Users user) {
        try {
        	// 사용자 인증
            String token = userService.authenticateUser(user); // JWT 토큰 생성
            
            // 토큰 반환
            return ResponseEntity.ok(new ResponseDto(token));  // Return JWT token
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
       
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // 클라이언트 측에서 토큰 삭제 유도 (서버는 처리할 필요 없음)
        return ResponseEntity.ok("Logged out successfully");
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Users userDto, Authentication authentication) {
    	String currentUsername = authentication.getName();
    	Users user = userService.updateUserProfile(currentUsername, userDto);
    	
    	return ResponseEntity.ok("Profile updated successfully");
    }
}