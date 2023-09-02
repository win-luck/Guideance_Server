package com.spring.guideance.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateArticleDto {

    private String title;
    private String contents;
    private List<String> tags;

    public CreateArticleDto(String title, String contents, List<String> tags) {
        this.title = title;
        this.contents = contents;
        this.tags = tags;
    }
}
