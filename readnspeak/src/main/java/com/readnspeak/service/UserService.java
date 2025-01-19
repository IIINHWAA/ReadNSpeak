package com.readnspeak.service;

import com.readnspeak.JwtUtil.JwtUtility;
import com.readnspeak.dto.LoginDto;
import com.readnspeak.dto.updateDto;
import com.readnspeak.entity.Users;
import com.readnspeak.repository.UserRepository;

import lombok.AllArgsConstructor;
import net.bytebuddy.asm.Advice.Exit;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.ott.GenerateOneTimeTokenFilter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtility jwtUtility;

    public Users registerUser(Users user) {
        // Check if the username already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Already exist");
        }

        // Create a new User entity
        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        // Hash the password using BCrypt
        newUser.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        newUser.setEmail(user.getEmail());
        newUser.setVerified(true);

        // Save the user to the database
        return userRepository.save(newUser);
    }

    public Map<String, String> authenticateUser(LoginDto logindto) {
        Users authuser = userRepository.findByUsername(logindto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (passwordEncoder.matches(logindto.getPassword_hash(), authuser.getPassword_hash())) {
        	// Generate JWT access token and refresh token
        	String accessToken = jwtUtility.generateToken(authuser);
            String refreshToken = jwtUtility.generateRefreshToken(authuser); // Generate refresh token
            
            // Create a map to return both tokens
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            
            return tokens; // Return both tokens
        } else {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }
    
    public Users updateUserProfile(String username, updateDto user) {
    	
    	Users existinguser = userRepository.findByUsername(username)
    			.orElseThrow(() -> new IllegalArgumentException(username));
    	if (user.getUsername() != null) {
    		existinguser.setUsername(user.getUsername());
    	}
    	if (user.getEmail() != null) {
    		existinguser.setEmail(user.getEmail());
    	}
    	if (user.getPassword_hash() != null) {
    		existinguser.setPassword_hash(user.getPassword_hash());
    	}
    	
    	return userRepository.save(existinguser);
    }
    
    public void logoutdeleterefresh(String username) {
    	try {
            jwtUtility.deleteRefreshToken(username);
        } catch (IllegalStateException e) {
            // Redis에서 refresh token 삭제 실패 시 예외 처리
            throw new IllegalStateException("Failed to delete refresh token for user: " + username, e);
        } catch (Exception e) {
            // 그 외의 예외 처리
            throw new RuntimeException("Unexpected error occurred during logout for user: " + username, e);
        }
    }
    
    
}