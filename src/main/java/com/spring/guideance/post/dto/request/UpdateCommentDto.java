package com.spring.guideance.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCommentDto {

    private Long userId;
    private String contents;

    public UpdateCommentDto(Long userId, String contents) {
        this.userId = userId;
        this.contents = contents;
    }
}
