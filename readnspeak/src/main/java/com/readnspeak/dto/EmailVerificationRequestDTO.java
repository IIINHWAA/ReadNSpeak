package com.readnspeak.dto;

import com.readnspeak.entity.Users;

import lombok.Data;

@Data
public class EmailVerificationRequestDTO {
	
	private String username;
    private String email;
    private String password_hash;
    private String token;

    // Getters and Setters
}