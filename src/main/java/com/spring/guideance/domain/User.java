package com.spring.guideance.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "user", timeToLive = 100) // Redis에 저장될 객체임을 명시, timeToLive는 100초 동안 Redis에 저장됨을 의미
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
