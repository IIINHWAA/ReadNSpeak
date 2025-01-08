package com.readnspeak.service;

import com.readnspeak.dto.LoginDto;
import com.readnspeak.dto.UserDto;
import com.readnspeak.entity.User;
import com.readnspeak.repository.UserRepository;
import com.readnspeak.security.JWTUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.ott.GenerateOneTimeTokenFilter;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtility jwtUtility;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtility jwtUtility) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtility = jwtUtility;
    }

    public User registerUser(UserDto userDto) {
        // 검증
        validateUser(userDto);

        // 비밀번호 해싱
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        // User 객체 생성 및 저장
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword_hash(encodedPassword);

        return userRepository.save(user);
    }
    
    public String login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword_hash())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // JWT 토큰 생성 (예시)
        return jwtUtility.generateToken(user);
    }

    private void validateUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }
        if (userDto.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
    }
}