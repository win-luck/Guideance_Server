package com.spring.guideance.post.domain;

import com.spring.guideance.tag.domain.ArticleTag;
import com.spring.guideance.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// @RedisHash(value = "article", timeToLive = 100)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String contents;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "article", cascade = {CascadeType.REMOVE}) // 글에 담긴 댓글, 글이 삭제되면 댓글도 삭제
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = {CascadeType.REMOVE}) // 글이 가진 좋아요, 글이 삭제되면 좋아요도 삭제
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = {CascadeType.REMOVE}) // 글이 가진 태그, 글이 삭제되면 글_태그도 삭제
    private List<ArticleTag> articleTags = new ArrayList<>();

    // 생성 메서드
    public static Article createArticle(String title, String contents) {
        Article article = new Article();
        article.title = title;
        article.contents = contents;
        return article;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // 수정 메서드
    public void updateArticle(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isAuthor(User user) {
        return this.user.equals(user);
    }
}
