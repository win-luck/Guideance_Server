package com.spring.guideance.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateArticleDto {

    private String title;
    private String contents;

    public UpdateArticleDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
