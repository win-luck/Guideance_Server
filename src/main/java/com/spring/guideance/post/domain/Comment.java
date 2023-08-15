package com.spring.guideance.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.guideance.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
// @RedisHash(value = "comment", timeToLive = 100)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 댓글 작성자

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article; // 댓글이 달린 게시글

    private String contents; // 댓글 내용

    private LocalDateTime createdAt;

    // 생성 메서드
    public static Comment createComment(String contents, User user, Article article) {
        Comment comment = new Comment();
        comment.contents = contents;
        comment.user = user;
        comment.article = article;
        comment.createdAt = LocalDateTime.now();
        return comment;
    }

    // 수정 메서드
    public void updateComment(String contents) {
        this.contents = contents;
    }

    public boolean isAuthor(User user) {
        return this.user.equals(user);
    }
}
