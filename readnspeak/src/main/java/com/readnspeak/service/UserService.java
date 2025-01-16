package com.readnspeak.service;

import com.readnspeak.JwtUtil.JwtUtility;
import com.readnspeak.entity.Users;
import com.readnspeak.repository.UserRepository;

import lombok.AllArgsConstructor;
import net.bytebuddy.asm.Advice.Exit;

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

        // Save the user to the database
        return userRepository.save(newUser);
    }

    public String authenticateUser(Users user) {
        Users authuser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (passwordEncoder.matches(user.getPassword_hash(), authuser.getPassword_hash())) {
            // Generate and return JWT token if authentication is successful
            return jwtUtility.generateToken(user);
        } else {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }
    
    public Users updateUserProfile(String username, Users user) {
    	
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
    
    
}