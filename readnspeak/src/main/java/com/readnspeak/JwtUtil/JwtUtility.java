package com.readnspeak.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.readnspeak.entity.User;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtility {

	@Value("${jwt.secret}")
	private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;
    
    private Key key;
    
    @PostConstruct
    public void init() {
        if (jwtSecret != null) {
            this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        } else {
            throw new IllegalArgumentException("JWT secret is not provided");
        }
    }
    
    // JWT 토큰 생성
    public String generateToken(User user) {
    	
        return Jwts.builder()
                .setSubject(String.valueOf(user.getUsername())) // 사용자 ID
                .claim("username", user.getUsername()) // 사용자명
                .claim("role", user.getRole()) // 사용자 역할
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // 토큰 만료 시간 (1시간)
                .signWith(key)
                .compact(); // 토큰 생성
    }

    // JWT 토큰에서 사용자 정보(user_id) 추출
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // JWT 토큰의 만료일자 확인
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // JWT 토큰에서 Claims 추출
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 토큰 검증
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
