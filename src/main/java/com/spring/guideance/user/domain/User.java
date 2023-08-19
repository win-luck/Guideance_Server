package com.spring.guideance.user.domain;

import com.spring.guideance.post.domain.Article;
import com.spring.guideance.post.domain.Comment;
import com.spring.guideance.post.domain.Likes;
import com.spring.guideance.tag.domain.UserTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE}) // 유저가 탈퇴하면 댓글도 삭제
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE}) // 유저가 탈퇴하면 글도 삭제
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE}) // 유저가 탈퇴하면 좋아요도 삭제
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE}) // 유저가 탈퇴하면 유저_알림도 삭제
    private List<UserNotice> userNotices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE}) // 유저가 탈퇴하면 유저_태그도 삭제
    private List<UserTag> userTags = new ArrayList<>();

    // 생성 메서드
    public static User createUser(String name, String email) {
        User user = new User();
        user.name = name;
        user.email = email;
        return user;
    }

    // 회원정보 수정
    public void updateUser(String name, String profileImage) {
        this.name = name;
        if(profileImage != null) {
            this.profileImage = profileImage;
        }
    }

    // 프로필사진 세팅
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
