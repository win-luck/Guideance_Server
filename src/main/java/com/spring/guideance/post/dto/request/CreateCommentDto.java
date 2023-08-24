package com.spring.guideance.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentDto {

    private Long userId;
    private String contents;

    public CreateCommentDto(Long userId, String contents) {
        this.userId = userId;
        this.contents = contents;
    }
}
