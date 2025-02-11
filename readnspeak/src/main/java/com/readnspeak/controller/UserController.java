package com.readnspeak.controller;

import com.readnspeak.JwtUtil.JwtUtility;
import com.readnspeak.dto.EmailVerificationRequestDTO;
import com.readnspeak.dto.LoginDto;
import com.readnspeak.dto.PasswordDto;
import com.readnspeak.dto.ResponseDto;
import com.readnspeak.dto.updateDto;
import com.readnspeak.entity.Users;
import com.readnspeak.repository.UserRepository;
import com.readnspeak.service.EmailVerificationService;
import com.readnspeak.service.UserService;

import java.time.LocalDateTime;
import java.util.Map;
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
    public ResponseEntity<?> registerUser(@RequestBody String email) {
        try {
            // 1. 이메일 인증 코드 보내기
            emailVerificationService.sendVerificationEmail(email);

            return ResponseEntity.ok("Verification email sent, please verify your email to complete registration.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
	
	@PostMapping("/register")
    public ResponseEntity<?> verifyEmail(@RequestBody EmailVerificationRequestDTO emailVerificationRequest) {
        try {
            // 2. 이메일 인증 코드 검증
            boolean isValid = emailVerificationService.verifyToken(emailVerificationRequest.getEmail(), emailVerificationRequest.getToken());
            if (isValid) {
                // 3. 사용자 등록
                Users newUser = userService.registerUser(emailVerificationRequest);
                return ResponseEntity.ok(newUser);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto logindto) {
        try {
        	// 사용자 인증
        	Map<String, String> tokens = userService.authenticateUser(logindto); //토큰 생성
            
            // 토큰 반환
        	return ResponseEntity.ok(new ResponseDto(tokens));  // Return both accessToken and refreshToken
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
       
    @PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
    	try {
            // 로그아웃 처리 및 Refresh Token 삭제
            userService.logoutdeleterefresh(authentication.getName());
            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            // 그 외의 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    @PutMapping("/updateprofile")
    public ResponseEntity<?> updateProfile(@RequestBody updateDto userDto, Authentication authentication) {
    	String currentUsername = authentication.getName();
    	Users updateUser = userService.updateUserProfile(currentUsername, userDto);
    	userService.logoutdeleterefresh(currentUsername);
    	
    	return ResponseEntity.ok(updateUser);
    }
    
    @GetMapping("/readprofile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        String currentUsername = authentication.getName(); // 현재 로그인된 사용자
        Users user = userService.getUserByUsername(currentUsername);
        return ResponseEntity.ok(user);
    }

    
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody PasswordDto passwordDto, Authentication authentication) {
        try {
            String currentUsername = authentication.getName(); // 현재 로그인한 사용자 이름
            userService.deleteUser(currentUsername, passwordDto.getPassword()); // 비밀번호와 함께 서비스로 전달
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the user: " + e.getMessage());
        }
    }

    
}