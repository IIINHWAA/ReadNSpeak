package com.readnspeak.service;

import com.readnspeak.entity.EmailVerificationToken;
import com.readnspeak.repository.EmailVerificationTokenRepository;
import com.readnspeak.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailVerificationService {

    private final JavaMailSender emailSender;
    private final EmailVerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public EmailVerificationService(JavaMailSender emailSender,
                                    EmailVerificationTokenRepository tokenRepository,
                                    UserRepository userRepository) {
        this.emailSender = emailSender;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    // 이메일 인증 토큰 생성 및 발송
    public void sendVerificationEmail(String email) {
        String token = generateVerificationToken();
        EmailVerificationToken verificationToken = new EmailVerificationToken();
        verificationToken.setEmail(email);
        verificationToken.setToken(token);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(15)); // 토큰 만료 시간 설정
        tokenRepository.save(verificationToken);

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

    // 인증 코드 검증
    public boolean verifyToken(String email, String token) {
        EmailVerificationToken verificationToken = tokenRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Verification token not found"));
        
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
        	tokenRepository.delete(verificationToken);
            throw new IllegalArgumentException("Token has expired");
        }
        System.out.println(verificationToken.getToken().equals(token));
        return verificationToken.getToken().equals(token);
    }
}
