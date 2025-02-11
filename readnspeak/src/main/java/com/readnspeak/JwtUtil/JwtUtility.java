package com.readnspeak.JwtUtil;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
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
    private long jwtRefreshExpiration; 

    private Key key;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String CLAIM_USERNAME = "username";
    private static final String CLAIM_ROLE = "role";

    public JwtUtility(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init() {
        if (jwtSecret == null || jwtSecret.isEmpty()) {
            throw new IllegalArgumentException("JWT secret is not provided");
        }
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /** ✅ JWT 토큰 생성 (Access & Refresh 공통 로직) */
    private String createToken(Users user, long expiration) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getUser_id()))
                .claim(CLAIM_USERNAME, user.getUsername())
                .claim(CLAIM_ROLE, user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    /** ✅ Access Token 생성 */
    public String generateToken(Users user) {
        return createToken(user, jwtExpiration);
    }

    /** ✅ Refresh Token 생성 & Redis 저장 */
    public String generateRefreshToken(Users user) {
        String refreshToken = createToken(user, jwtRefreshExpiration);
        redisTemplate.opsForValue().set(user.getUsername(), refreshToken, jwtRefreshExpiration, TimeUnit.MILLISECONDS);
        return refreshToken;
    }

    /** ✅ Redis에서 Refresh Token 조회 */
    public String getRefreshTokenFromRedis(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    /** ✅ Refresh Token 유효성 검증 */
    public boolean validateRefreshToken(String token, String username) {
        String storedToken = getRefreshTokenFromRedis(username);
        return token.equals(storedToken) && !isTokenExpired(token);
    }

    /** ✅ JWT 토큰에서 Claim 추출 */
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /** ✅ JWT 토큰에서 username 추출 */
    public String extractUsername(String token) {
        return extractClaims(token).get(CLAIM_USERNAME, String.class);
    }

    /** ✅ JWT 토큰에서 role 추출 */
    public String extractRole(String token) {
        return extractClaims(token).get(CLAIM_ROLE, String.class);
    }

    /** ✅ JWT 토큰 만료 여부 확인 */
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    /** ✅ Access Token 검증 */
    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    /** ✅ 새로운 Access Token 발급 */
    public String refreshAccessToken(String refreshToken, Users user) {
        if (!validateRefreshToken(refreshToken, user.getUsername())) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }
        return generateToken(user);
    }

    /** ✅ Refresh Token 삭제 (로그아웃 시 사용) */
    public void deleteRefreshToken(String username) {
        redisTemplate.delete(username);
    }
}
