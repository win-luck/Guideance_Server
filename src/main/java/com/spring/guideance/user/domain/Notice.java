package com.spring.guideance.user.domain;

import com.spring.guideance.util.NoticeType;
import com.spring.guideance.util.exception.NoticeException;
import com.spring.guideance.util.exception.ResponseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private NoticeType type; // 게시글에 좋아요 발생, 게시글에 댓글 발생, 구독한 태그에 새로운 게시글 등장

    private String title;

    private String contents;

    @OneToMany(mappedBy = "user")
    private List<UserNotice> userNotices = new ArrayList<>();

    // 생성 메서드
    public static Notice createNotice(int type, String userName, String contents) {
        Notice notice = new Notice();
        notice.type = NoticeType.findByCode(type);
        switch (type) {
            case 1:
                notice.title = userName + "님이 내 게시물에 좋아요를 눌렀습니다.";
                notice.contents = contents; // 게시물 제목
                break;
            case 2:
                notice.title = userName + "님이 내 게시물에 댓글을 남겼습니다.";
                notice.contents = contents; // 댓글 내용
                break;
            case 3:
                notice.title = "구독 중인 태그에 새 게시물이 추가되었습니다.";
                notice.contents = contents; // 게시물 제목
                break;
            default:
                throw new NoticeException(ResponseCode.NOTICE_TYPE_WRONG);
        }
        return notice;
    }
}
