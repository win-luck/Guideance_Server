package com.spring.guideance.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateArticleDto {

    private Long userId;
    private String title;
    private String contents;

    public UpdateArticleDto(Long userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
    }
}
