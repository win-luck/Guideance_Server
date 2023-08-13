package com.spring.guideance.post.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCommentDto {
    private Long userId;
    private Long commentId;
    private String contents;

    public UpdateCommentDto(Long userId, Long commentId, String contents) {
        this.userId = userId;
        this.commentId = commentId;
        this.contents = contents;
    }
}
