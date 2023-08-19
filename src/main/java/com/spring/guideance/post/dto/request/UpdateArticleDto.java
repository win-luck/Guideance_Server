package com.spring.guideance.post.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
