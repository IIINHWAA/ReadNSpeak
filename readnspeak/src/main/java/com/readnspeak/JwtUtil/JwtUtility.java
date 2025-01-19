package com.readnspeak.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.readnspeak.entity.Users;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtility {

	@Value("${jwt.secret}")
	private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;
    
    @Value("${jwt.refreshExpiration}")
    private long jwtRefreshExpiration; // Refresh Token의 유효 기간 추가
    
    private Key key;
    private final RedisTemplate<String, String> redisTemplate;
    
    public JwtUtility(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @PostConstruct
    public void init() {
        if (jwtSecret != null) {
            this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        } else {
            throw new IllegalArgumentException("JWT secret is not provided");
        }
    }
    
    // JWT 토큰 생성
    public String generateToken(Users user) {
    	
        return Jwts.builder()
                .setSubject(String.valueOf(user.getUser_id())) // 사용자 id
                .claim("username", user.getUsername()) // 사용자명
                .claim("role", user.getRole()) // 사용자 역할
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // 토큰 만료 시간 (1시간)
                .signWith(key)
                .compact(); // 토큰 생성
    }
    
    // Refresh Token 생성
    public String generateRefreshToken(Users user) {
        String refreshToken = Jwts.builder()
                .setSubject(String.valueOf(user.getUser_id()))
                .claim("username", user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpiration))
                .signWith(key)
                .compact();

        // Redis에 Refresh Token 저장 (유효 기간 설정)
        redisTemplate.opsForValue().set(user.getUsername(), refreshToken, jwtRefreshExpiration, TimeUnit.MILLISECONDS);

        return refreshToken;
    }
    
 // Redis에서 Refresh Token을 가져옴
    public String getRefreshTokenFromRedis(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    // Refresh Token 유효성 검증
    public boolean validateRefreshToken(String token, String username) {
        // Redis에서 저장된 Refresh Token을 조회하여 비교
        String storedToken = getRefreshTokenFromRedis(username);
        return storedToken != null && storedToken.equals(token);
    }

    // JWT 토큰에서 사용자 정보(username) 추출
    public String extractUsername(String token) {
        return extractClaims(token).get("username",String.class);
    }

    // JWT 토큰의 만료일자 확인
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
    
    public String extractRole(String token)	{
    	return (String) extractClaims(token).get("role");
    }

    // JWT 토큰에서 Claims 추출
    public Claims extractClaims(String token) {
    	if (token == null || token.trim().isEmpty()) {
    	    throw new IllegalArgumentException("JWT token is null or empty");
    	}
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException e) {
            throw new IllegalArgumentException("Invalid JWT token format", e);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("JWT token has expired", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse JWT token", e);
        }
    }

    // JWT 토큰 검증
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
    
 // Refresh Token 검증
    public boolean validateRefreshToken(String token) {
        return !isTokenExpired(token); // Refresh Token이 만료되지 않았으면 유효한 것으로 판단
    }
    
 // 새로운 Access Token 발급
    public String refreshAccessToken(String refreshToken, Users user) {
        if (validateRefreshToken(refreshToken)) {
            return generateToken(user); // 유효한 Refresh Token이면 새로운 Access Token 생성
        } else {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }
    }
    
 // Refresh Token 삭제 (로그아웃 시 사용)
    public void deleteRefreshToken(String username) {
    	try {
            redisTemplate.delete(username);
        } catch (Exception e) {
            // Redis 연결 문제 등 예외를 처리
            throw new IllegalStateException("Failed to delete refresh token for user: " + username, e);
        }
    }
    
    
}
