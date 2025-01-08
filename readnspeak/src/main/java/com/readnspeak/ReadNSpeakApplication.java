package com.readnspeak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ReadNSpeakApplication {

	public static void main(String[] args) {
		// .env 파일을 로드
        Dotenv dotenv = Dotenv.load();

        // .env 파일에서 값을 가져와 시스템 속성으로 설정
        String dbUrl = dotenv.get("DATABASE_URL");
        String dbPassword = dotenv.get("DATABASE_PASSWORD");

        System.setProperty("DATABASE_URL", dbUrl);
        System.setProperty("DATABASE_PASSWORD", dbPassword);

        // Spring Boot 애플리케이션 실행
        SpringApplication.run(ReadNSpeakApplication.class, args);
	}

}
