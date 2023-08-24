package com.spring.guideance.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteCommentDto {

    private Long userId;

    public DeleteCommentDto(Long userId, Long commentId) {
        this.userId = userId;
    }
}
