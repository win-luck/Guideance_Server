package com.spring.guideance.comment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
@Getter @Setter
// @RedisHash(value = "comment", timeToLive = 100)
public class Comment {

    @Id @GeneratedValue
    private String id;

    private String content;
    private String articleId;

    public Comment(String content, String articleId) {
        this.content = content;
        this.articleId = articleId;
    }

    public Comment() {

    }
}
