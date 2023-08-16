package com.spring.guideance.util;

public enum NoticeType {
    LIKE(1, "Like"),
    COMMENT(2, "Comment"),
    TAG_SUB(3, "TagSub");

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
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
