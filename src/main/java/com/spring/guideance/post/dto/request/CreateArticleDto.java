package com.spring.guideance.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateArticleDto {

    private Long userId;
    private String title;
    private String contents;
    private List<String> tags;

    public CreateArticleDto(Long userId, String title, String contents, List<String> tags) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.tags = tags;
    }
}
