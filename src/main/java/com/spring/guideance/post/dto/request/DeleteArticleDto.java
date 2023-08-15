package com.spring.guideance.post.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteArticleDto {
    private Long userId;
    private Long articleId;

    public DeleteArticleDto(Long userId, Long articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }
}
