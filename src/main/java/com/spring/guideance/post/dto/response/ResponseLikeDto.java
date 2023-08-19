package com.spring.guideance.post.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseLikeDto {
    private Long userId;
    private Long articleId;
    private LocalDateTime createdAt;

    private ResponseLikeDto(Long userId, Long articleId, LocalDateTime createdAt) {
        this.userId = userId;
        this.articleId = articleId;
        this.createdAt = createdAt;
    }

    public static ResponseLikeDto of(Long userId, Long articleId, LocalDateTime createdAt) {
        return new ResponseLikeDto(userId, articleId, createdAt);
    }
}
