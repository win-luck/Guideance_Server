package com.spring.guideance.post.domain;

import com.spring.guideance.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

// @RedisHash(value = "comment", timeToLive = 100)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 댓글 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article; // 댓글이 달린 게시글

    private String contents; // 댓글 내용

    @CreatedDate
    private LocalDateTime createdAt;

    // 생성 메서드
    public static Comment createComment(String contents, User user, Article article) {
        Comment comment = new Comment();
        comment.contents = contents;
        comment.user = user;
        comment.article = article;
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
