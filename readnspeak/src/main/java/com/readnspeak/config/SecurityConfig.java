package com.readnspeak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // CSRF 보호를 비활성화 (테스트용으로만 사용)
            .authorizeRequests()
                .requestMatchers("/api/users").permitAll()  // 특정 엔드포인트 접근 허용
                .anyRequest().authenticated();
    }**/
}