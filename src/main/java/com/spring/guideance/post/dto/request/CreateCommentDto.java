package com.spring.guideance.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentDto {

    private String contents;

    public CreateCommentDto(String contents) {
        this.contents = contents;
    }
}
