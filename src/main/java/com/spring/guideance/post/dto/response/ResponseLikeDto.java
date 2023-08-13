package com.spring.guideance.post.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseLikeDto {
    private Long userId;
    private Long articleId;
    private LocalDateTime createdAt;

    public ResponseLikeDto(Long userId, Long articleId, LocalDateTime createdAt) {
        this.userId = userId;
        this.articleId = articleId;
        this.createdAt = createdAt;
    }
}
