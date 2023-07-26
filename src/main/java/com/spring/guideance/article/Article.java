package com.spring.guideance.article;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@RedisHash(value = "article", timeToLive = 100)
public class Article {

    @Id
    private String id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Integer readCount;
    private Integer likeCount;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.readCount = 0;
        this.likeCount = 0;
    }
}
