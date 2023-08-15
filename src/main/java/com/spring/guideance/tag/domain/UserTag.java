package com.spring.guideance.tag.domain;

import com.spring.guideance.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class UserTag { // user와 tag의 다대다 관계로 인해 생긴 중간 테이블, 유저가 구독한 태그를 관리

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag; // 구독한 태그

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 구독한 유저

    private LocalDateTime createdAt;

    public static UserTag createUserTag(Tag tag, User user) {
        UserTag userTag = new UserTag();
        userTag.tag = tag;
        userTag.user = user;
        userTag.createdAt = LocalDateTime.now();
        return userTag;
    }
}
