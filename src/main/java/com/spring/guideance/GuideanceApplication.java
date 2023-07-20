package com.spring.guideance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RedisConfig.class)
public class GuideanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuideanceApplication.class, args);
	}

}
