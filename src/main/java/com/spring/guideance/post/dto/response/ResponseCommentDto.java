package com.spring.guideance.post.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseCommentDto {
    private String authorName;
    private String contents;
    private LocalDateTime createdAt;

    public ResponseCommentDto(String authorName, String contents, LocalDateTime createdAt) {
        this.authorName = authorName;
        this.contents = contents;
        this.createdAt = createdAt;
    }
}
