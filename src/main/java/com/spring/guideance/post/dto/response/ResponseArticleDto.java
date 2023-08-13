package com.spring.guideance.post.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ResponseArticleDto {
    private String title;
    private String contents;
    private String authorName;
    private List<ResponseLikeDto> likes;
    private List<ResponseCommentDto> comments;
    private LocalDateTime createdAt;

    public ResponseArticleDto(String title, String contents, String authorName, List<ResponseLikeDto> likes, List<ResponseCommentDto> comments, LocalDateTime createdAt) {
        this.title = title;
        this.contents = contents;
        this.authorName = authorName;
        this.likes = likes;
        this.comments = comments;
        this.createdAt = createdAt;
    }
}
