package com.spring.guideance.post.dto.response;

import com.spring.guideance.post.domain.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseCommentDto {
    private Long commentId;
    private Long articleId;
    private String articleTitle;
    private String authorName;
    private String contents;
    private LocalDateTime createdAt;

    public ResponseCommentDto(Comment comment) {
        this.commentId = comment.getId();
        this.articleId = comment.getArticle().getId();
        this.articleTitle = comment.getArticle().getTitle();
        this.authorName = comment.getUser().getName();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
    }
}
