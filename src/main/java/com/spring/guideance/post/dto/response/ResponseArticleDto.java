package com.spring.guideance.post.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ResponseArticleDto {
    private Long articleId;
    private String title;
    private String contents;
    private String authorName;
    private List<String> tags;
    private List<ResponseLikeDto> likes;
    private List<ResponseCommentDto> comments;
    private LocalDateTime createdAt;

    public ResponseArticleDto(Long articleId, String title, String contents, String authorName, List<String> tags, List<ResponseLikeDto> likes, List<ResponseCommentDto> comments, LocalDateTime createdAt) {
        this.articleId = articleId;
        this.title = title;
        this.contents = contents;
        this.authorName = authorName;
        this.tags = tags;
        this.likes = likes;
        this.comments = comments;
        this.createdAt = createdAt;
    }
}
