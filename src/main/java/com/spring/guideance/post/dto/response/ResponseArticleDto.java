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
    private List<String> tags;
    private List<ResponseLikeDto> likes;
    private List<ResponseCommentDto> comments;
    private LocalDateTime createdAt;

    private ResponseArticleDto(Long articleId, String title, String contents, String authorName, List<String> tags, List<ResponseLikeDto> likes, List<ResponseCommentDto> comments, LocalDateTime createdAt) {
        this.articleId = articleId;
        this.title = title;
        this.contents = contents;
        this.authorName = authorName;
        this.tags = tags;
        this.likes = likes;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    public static ResponseArticleDto of(Long articleId, String title, String contents, String authorName, List<String> tags, List<ResponseLikeDto> likes, List<ResponseCommentDto> comments, LocalDateTime createdAt) {
        return new ResponseArticleDto(articleId, title, contents, authorName, tags, likes, comments, createdAt);
    }

    public static ResponseArticleDto from(Article article) {
        return ResponseArticleDto.of(
                article.getId(),
                article.getTitle(),
                article.getContents(),
                article.getUser().getName(),
                article.getArticleTags().stream().map(articleTag -> articleTag.getTag().getTagName()).collect(Collectors.toList()),
                article.getLikes().stream().map(ResponseLikeDto::from).collect(Collectors.toList()),
                article.getComments().stream().map(ResponseCommentDto::from).collect(Collectors.toList()),
                article.getCreatedAt()
        );
    }
}
