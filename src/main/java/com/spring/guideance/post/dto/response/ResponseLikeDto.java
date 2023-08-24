package com.spring.guideance.post.dto.response;

import com.spring.guideance.post.domain.Likes;
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

    public static ResponseLikeDto from(Likes likes) {
        return ResponseLikeDto.of(
                likes.getUser().getId(),
                likes.getArticle().getId(),
                likes.getCreatedAt()
        );
    }
}
