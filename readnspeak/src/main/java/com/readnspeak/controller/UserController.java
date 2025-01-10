package com.readnspeak.controller;

import com.readnspeak.JwtUtil.JwtUtility;
import com.readnspeak.dto.ResponseDto;
import com.readnspeak.dto.UserDto;
import com.readnspeak.entity.User;
import com.readnspeak.service.UserService;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
 // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            User newUser = userService.registerUser(userDto);
            return ResponseEntity.ok(newUser);  // Return the created user object
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
        try {
            String token = userService.authenticateUser(userDto);
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
    public ResponseEntity<?> updateProfile(@RequestBody UserDto userDto, Authentication authentication) {
    	String currentUsername = authentication.getName();
    	User user = userService.updateUserProfile(currentUsername, userDto);
    	
    	return ResponseEntity.ok("Profile updated successfully");
    }
}