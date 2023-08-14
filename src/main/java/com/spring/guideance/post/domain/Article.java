package com.spring.guideance.post.domain;

import com.spring.guideance.post.dto.request.CreateArticleDto;
import com.spring.guideance.post.dto.request.UpdateArticleDto;
import com.spring.guideance.tag.domain.ArticleTag;
import com.spring.guideance.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// @RedisHash(value = "article", timeToLive = 100)
@Entity
@Getter @Setter
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String contents;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "article") // 글에 담긴 댓글
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article") // 글이 가진 좋아요
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "article") // 글이 가진 태그
    private List<ArticleTag> articleTags = new ArrayList<>();

    // 생성 메서드
    public static Article createArticle(CreateArticleDto createArticleDto) {
        Article article = new Article();
        article.title = createArticleDto.getTitle();
        article.contents = createArticleDto.getContents();
        article.createdAt = LocalDateTime.now();
        return article;
    }

    // 수정 메서드
    public void updateArticle(UpdateArticleDto updateArticleDto) {
        this.title = updateArticleDto.getTitle();
        this.contents = updateArticleDto.getContents();
    }

    public boolean isAuthor(User user) {
        return this.user.equals(user);
    }
}
