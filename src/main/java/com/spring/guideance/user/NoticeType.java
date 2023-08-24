package com.spring.guideance.user;

import com.spring.guideance.util.exception.NoticeException;
import com.spring.guideance.util.exception.ResponseCode;

public enum NoticeType {
    LIKE(1, "Like"),
    COMMENT(2, "Comment"),
    TAG_SUB(3, "TagSub"),
    DEFAULT(4, "Default");

    private final int code;
    private final String description;

    NoticeType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static NoticeType findByCode(int code) {
        for (NoticeType noticeType : NoticeType.values()) {
            if (noticeType.getCode() == code) {
                return noticeType;
            }
        }

        throw new NoticeException(ResponseCode.NOTICE_NOT_FOUND);
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
