package com.spring.guideance.user.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private int type; // 게시글에 좋아요 발생, 게시글에 댓글 발생, 구독한 태그에 새로운 게시글 등장

    private String title;

    private String contents;

    @OneToMany(mappedBy = "user")
    private List<UserNotice> userNotices = new ArrayList<>();

    // 생성 메서드
    public static Notice createNotice(int type, String title, String contents) {
        Notice notice = new Notice();
        notice.type = type;
        notice.title = title;
        notice.contents = contents;
        return notice;
    }
}
