package com.spring.guideance.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.guideance.post.domain.Article;
import com.spring.guideance.post.domain.Comment;
import com.spring.guideance.post.domain.Likes;
import com.spring.guideance.tag.domain.UserTag;
import com.spring.guideance.user.dto.request.CreateUserDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// @RedisHash(value = "user", timeToLive = 100) // Redis에 저장될 객체임을 명시, timeToLive는 100초 동안 Redis에 저장됨을 의미
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name; // 닉네임

    private String email; // 이메일

    private String profileImage; // 프로필 이미지

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserNotice> userNotices = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserTag> userTags = new ArrayList<>();

    // 생성 메서드
    public static User createUser(CreateUserDto createUserDto) {
        User user = new User();
        user.name = createUserDto.getName();
        user.email = createUserDto.getEmail();
        return user;
    }

    // 회원정보 수정
    public void updateUser(String name) {
        this.name = name;
    }

    // 프로필사진 세팅
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
