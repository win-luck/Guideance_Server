package com.spring.guideance.post.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCommentDto {
    private Long userId;
    private String contents;

    public CreateCommentDto(Long userId, String contents) {
        this.userId = userId;
        this.contents = contents;
    }
}
