package com.spring.guideance.post.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spring.guideance.post.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ResponseSimpleArticleDto implements Serializable {

    private Long articleId;
    private String title;
    private String contents;
    private String authorName;
    private String authorProfileImage;
    private int likesCount;
    private int commentCount;
    private LocalDateTime createdAt;
    private boolean isLiked;

    private ResponseSimpleArticleDto(Article article, boolean isLiked) {
        this.articleId = article.getId();
        this.title = article.getTitle();
        this.contents = article.getContents();
        this.authorName = article.getUser().getName();
        this.authorProfileImage = article.getUser().getProfileImage();
        this.likesCount = article.getLikes().size();
        this.commentCount = article.getComments().size();
        this.createdAt = article.getCreatedAt();
        this.isLiked = isLiked;
    }

    public static ResponseSimpleArticleDto from(Article article, boolean isLiked) {
        return new ResponseSimpleArticleDto(article, isLiked);
    }

    public static ResponseSimpleArticleDto of(Long articleId, String title, String contents, String authorName, String authorProfileImage, int likesCount, int commentCount, LocalDateTime createdAt, boolean isLiked) {
        return new ResponseSimpleArticleDto(articleId, title, contents, authorName, authorProfileImage, likesCount, commentCount, createdAt, isLiked);
    }
}
