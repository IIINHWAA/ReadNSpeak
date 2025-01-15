package com.readnspeak.entity;

import lombok.Data;

@Data
public class EmailVerificationRequest {

    private String email;
    private String token;
    private Users user; // 사용자 정보 (사용자가 이미 가입 양식을 작성한 후)

    // Getters and Setters
}