package com.spring.guideance.post.dto.response;

import com.spring.guideance.post.domain.Article;
import lombok.Getter;

@Getter
public class ResponseSimpleArticleDto {

    private Long articleId;
    private String title;
    private String contents;
    private String authorName;
    private int likesCount;
    private int commentCount;
    private boolean isLiked;

    private ResponseSimpleArticleDto(Long articleId, String title, String contents, String authorName, int likesCount, int commentCount, boolean isLiked) {
        this.articleId = articleId;
        this.title = title;
        this.contents = contents;
        this.authorName = authorName;
        this.likesCount = likesCount;
        this.commentCount = commentCount;
        this.isLiked = isLiked;
    }

    private ResponseSimpleArticleDto(Article article, boolean isLiked) {
        this.articleId = article.getId();
        this.title = article.getTitle();
        this.contents = article.getContents();
        this.authorName = article.getUser().getName();
        this.likesCount = article.getLikes().size();
        this.commentCount = article.getComments().size();
        this.isLiked = isLiked;
    }

    public static ResponseSimpleArticleDto from(Article article, boolean isLiked) {
        return new ResponseSimpleArticleDto(article, isLiked);
    }

    public static ResponseSimpleArticleDto of(Long articleId, String title, String contents, String authorName, int likesCount, int commentCount, boolean isLiked) {
        return new ResponseSimpleArticleDto(articleId, title, contents, authorName, likesCount, commentCount, isLiked);
    }
}
