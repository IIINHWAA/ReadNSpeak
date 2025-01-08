package com.readnspeak.controller;

import com.readnspeak.dto.LoginDto;
import com.readnspeak.dto.LoginResponseDto;
import com.readnspeak.dto.UserDto;
import com.readnspeak.entity.User;
import com.readnspeak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    
    private UserService userService;
    
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            User newUser = userService.registerUser(userDto);
            return ResponseEntity.ok(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            String token = userService.login(loginDto);
            return ResponseEntity.ok(new LoginResponseDto(token, "Login successful"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // 클라이언트 측에서 토큰 삭제 유도 (서버는 처리할 필요 없음)
        return ResponseEntity.ok("Logged out successfully");
    }
}