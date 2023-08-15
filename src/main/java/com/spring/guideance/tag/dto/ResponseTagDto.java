package com.spring.guideance.tag.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseTagDto {
    private Long tagId;
    private String tagName;
    private int subscribeCount;
    private int likeCount;
    private boolean isSubscribed;

    public ResponseTagDto(Long tagId, String tagName, int subscribeCount, int likeCount) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.subscribeCount = subscribeCount;
        this.likeCount = likeCount;
    }

    public void setSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }
}
