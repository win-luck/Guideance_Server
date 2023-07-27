package com.spring.guideance.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int type; // 게시글에 좋아요 발생, 게시글에 댓글 발생, 구독한 태그에 새로운 게시글 등장

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private boolean checked; // 읽음 여부
}
