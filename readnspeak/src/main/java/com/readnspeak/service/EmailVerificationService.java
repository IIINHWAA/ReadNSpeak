package com.readnspeak.service;


import com.readnspeak.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class EmailVerificationService {

	private final JavaMailSender emailSender;
    private final StringRedisTemplate redisTemplate;
    private final UserRepository userRepository; 

    @Autowired
    public EmailVerificationService(JavaMailSender emailSender, StringRedisTemplate redisTemplate, UserRepository userRepository) {
        this.emailSender = emailSender;
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
    }

    // 이메일 인증 토큰 생성 및 발송
    public void sendVerificationEmail(String email) {
    	if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Already exist");
        }
    	String token = generateVerificationToken();

        // Redis에 인증 토큰 저장 (TTL 15분 설정)
    	redisTemplate.opsForValue().set(email, token, 15, TimeUnit.MINUTES);
        System.out.println("Token saved to Redis for email: " + email + " with token: " + token); // 로그로 확인
        sendEmail(email, token);
    }

    private String generateVerificationToken() {
        // 임의의 6자리 숫자 토큰 생성
        return String.format("%06d", new Random().nextInt(999999));
    }

    private void sendEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email Verification");
        message.setText("Your verification code is: " + token);
        emailSender.send(message);
    }
    
    public boolean verifyToken(String email, String token) {
        String storedToken = redisTemplate.opsForValue().get(email);
        System.out.println("Stored Token from Redis: " + storedToken);
        if (storedToken == null) {
            throw new IllegalArgumentException("Verification token not found or expired");
        }

        return storedToken.equals(token);
    }
}
