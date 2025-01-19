package com.readnspeak.dto;

import java.util.Map;

public class ResponseDto {
    private String accessToken;
    private String refreshToken;

    public ResponseDto(Map<String, String> tokens) {
        this.accessToken = tokens.get("accessToken");
        this.refreshToken = tokens.get("refreshToken");
    }

    // Getter and Setter methods
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
