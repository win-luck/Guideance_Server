package com.spring.guideance;

import com.spring.guideance.redis.RedisConfig;
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
