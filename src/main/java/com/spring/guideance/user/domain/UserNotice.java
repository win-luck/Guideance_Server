package com.spring.guideance.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserNotice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_notice_id")
    private Long id;

    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @CreatedDate
    private LocalDateTime createdAt;

    public void read() {
        this.isRead = true;
    }

    public static UserNotice createUserNotice(Notice notice, User user) {
        UserNotice userNotice = new UserNotice();
        userNotice.notice = notice;
        userNotice.user = user;
        return userNotice;
    }
}
