package com.spring.guideance.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteArticleDto {

    private Long userId;
    private Long articleId;

    public DeleteArticleDto(Long userId, Long articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }
}
