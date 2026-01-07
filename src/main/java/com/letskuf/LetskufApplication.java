package com.letskuf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// exclude = {SecurityAutoConfiguration.class} 설정 안하면 메인 페이지 진입할 때 로그인 부분 나와서 제외
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class LetskufApplication {

	public static void main(String[] args) {
		SpringApplication.run(LetskufApplication.class, args);
	}

}
