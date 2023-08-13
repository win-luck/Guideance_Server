package com.spring.guideance.post.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteCommentDto {
    private Long userId;
    private Long commentId;

    public DeleteCommentDto(Long userId, Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }
}
