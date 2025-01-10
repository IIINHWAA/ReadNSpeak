package com.readnspeak.service;

import com.readnspeak.JwtUtil.JwtUtility;
import com.readnspeak.dto.UserDto;
import com.readnspeak.entity.User;
import com.readnspeak.repository.UserRepository;

import net.bytebuddy.asm.Advice.Exit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.ott.GenerateOneTimeTokenFilter;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtility jwtUtility;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtility jwtUtility) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtility = jwtUtility;
    }

    public User registerUser(UserDto userDto) {
        // Check if the username already exists
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Create a new User entity
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        // Hash the password using BCrypt
        newUser.setPassword_hash(passwordEncoder.encode(userDto.getPassword()));
        newUser.setEmail(userDto.getEmail());

        // Save the user to the database
        return userRepository.save(newUser);
    }

    public String authenticateUser(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (passwordEncoder.matches(userDto.getPassword(), user.getPassword_hash())) {
            // Generate and return JWT token if authentication is successful
            return jwtUtility.generateToken(user);
        } else {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }
    
    public User updateUserProfile(String username, UserDto userDto) {
    	
    	User existinguser = userRepository.findByUsername(username)
    			.orElseThrow(() -> new IllegalArgumentException(username));
    	if (userDto.getUsername() != null) {
    		existinguser.setUsername(userDto.getUsername());
    	}
    	if (userDto.getEmail() != null) {
    		existinguser.setEmail(userDto.getEmail());
    	}
    	if (userDto.getPassword() != null) {
    		existinguser.setPassword_hash(userDto.getPassword());
    	}
    	
    	return userRepository.save(existinguser);
    }
}