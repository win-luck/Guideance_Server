package com.spring.guideance.post.dto.response;

import com.spring.guideance.post.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseCommentDto {

    private Long commentId;
    private Long articleId;
    private String articleTitle;
    private String authorName;
    private String authorProfileImage;
    private String contents;
    private LocalDateTime createdAt;

    private ResponseCommentDto(Comment comment) {
        this.commentId = comment.getId();
        this.articleId = comment.getArticle().getId();
        this.articleTitle = comment.getArticle().getTitle();
        this.authorName = comment.getUser().getName();
        this.authorProfileImage = comment.getUser().getProfileImage();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
    }

    public static ResponseCommentDto from(Comment comment) {
        return new ResponseCommentDto(comment);
    }
}
