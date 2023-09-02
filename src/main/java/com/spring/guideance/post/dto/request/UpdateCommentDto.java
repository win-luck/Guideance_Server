package com.spring.guideance.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCommentDto {

    private String contents;

    public UpdateCommentDto(String contents) {
        this.contents = contents;
    }
}
