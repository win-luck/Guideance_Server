package com.spring.guideance.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;

// @RedisHash(value = "user", timeToLive = 100) // Redis에 저장될 객체임을 명시, timeToLive는 100초 동안 Redis에 저장됨을 의미
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name; // 닉네임

    private String email; // 이메일

    @JsonIgnore
    private String password; // 비밀번호

    private int level; // 유저의 글쓰기 레벨
}
