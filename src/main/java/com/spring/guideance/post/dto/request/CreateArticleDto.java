package com.spring.guideance.post.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateArticleDto {
    private Long userId;
    private String title;
    private String contents;
    private List<String> tags;
}
