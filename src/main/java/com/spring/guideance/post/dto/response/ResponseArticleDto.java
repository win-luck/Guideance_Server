package com.spring.guideance.post.dto.response;

import com.spring.guideance.post.domain.Article;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseArticleDto {

    private Long articleId;
    private String title;
    private String contents;
    private String authorName;
    private String authorProfileImage;
    private List<String> tags;
    private List<ResponseLikeDto> likes;
    private List<ResponseCommentDto> comments;
    private LocalDateTime createdAt;
    private boolean isLiked;

    private ResponseArticleDto(Long articleId, String title, String contents, String authorName, String authorProfileImage, List<String> tags, List<ResponseLikeDto> likes, List<ResponseCommentDto> comments, LocalDateTime createdAt, boolean isLiked) {
        this.articleId = articleId;
        this.title = title;
        this.contents = contents;
        this.authorName = authorName;
        this.authorProfileImage = authorProfileImage;
        this.tags = tags;
        this.likes = likes;
        this.comments = comments;
        this.createdAt = createdAt;
        this.isLiked = isLiked;
    }

    public static ResponseArticleDto of(Long articleId, String title, String contents, String authorName, String authorProfileImage, List<String> tags, List<ResponseLikeDto> likes, List<ResponseCommentDto> comments, LocalDateTime createdAt, boolean isLiked) {
        return new ResponseArticleDto(articleId, title, contents, authorName, authorProfileImage, tags, likes, comments, createdAt, isLiked);
    }

    public static ResponseArticleDto from(Article article, boolean isLiked) {
        return ResponseArticleDto.of(
                article.getId(),
                article.getTitle(),
                article.getContents(),
                article.getUser().getName(),
                article.getUser().getProfileImage(),
                article.getArticleTags().stream().map(articleTag -> articleTag.getTag().getTagName()).collect(Collectors.toList()),
                article.getLikes().stream().map(ResponseLikeDto::from).collect(Collectors.toList()),
                article.getComments().stream().map(ResponseCommentDto::from).collect(Collectors.toList()),
                article.getCreatedAt(),
                isLiked
        );
    }
}
