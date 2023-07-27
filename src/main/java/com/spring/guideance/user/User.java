package com.spring.guideance.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.guideance.article.Article;
import com.spring.guideance.comment.Comment;
import com.spring.guideance.like.Likes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// @RedisHash(value = "user", timeToLive = 100) // Redis에 저장될 객체임을 명시, timeToLive는 100초 동안 Redis에 저장됨을 의미
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name; // 닉네임

    private String email; // 이메일

    @JsonIgnore
    private String password; // 비밀번호

    private int level; // 유저의 레벨

    private int exp; // 유저의 경험치

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Notice> likeArticles = new ArrayList<>();
}
