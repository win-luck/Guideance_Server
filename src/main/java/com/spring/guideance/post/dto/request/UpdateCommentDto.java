package com.spring.guideance.post.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCommentDto {

    private Long userId;
    private String contents;

    public UpdateCommentDto(Long userId, String contents) {
        this.userId = userId;
        this.contents = contents;
    }
}
