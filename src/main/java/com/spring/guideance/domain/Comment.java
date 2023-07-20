package com.spring.guideance.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "comment", timeToLive = 100)
public class Comment {

    @Id
    private String id;
    private String content;
    private String articleId;

    public Comment(String content, String articleId) {
        this.content = content;
        this.articleId = articleId;
    }
}
