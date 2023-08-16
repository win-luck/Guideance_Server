package com.spring.guideance.user.dto.response;

import com.spring.guideance.user.domain.UserNotice;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseNoticeDto {
    private Long id;
    private String type;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private boolean isRead;

    public ResponseNoticeDto(UserNotice userNotice) {
        this.id = userNotice.getId();
        this.type = userNotice.getNotice().getType().getDescription();
        this.title = userNotice.getNotice().getTitle();
        this.contents = userNotice.getNotice().getContents();
        this.createdAt = userNotice.getCreatedAt();
        this.isRead = userNotice.isRead();
    }
}
