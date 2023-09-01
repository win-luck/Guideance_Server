package com.spring.guideance.tag.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spring.guideance.tag.domain.UserTag;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonSerialize
@JsonDeserialize
public class ResponseTagDto implements Serializable {
    
    private Long tagId;
    private String tagName;
    private int articleCount;
    private int likeCount;
    private boolean isSubscribed;

    private ResponseTagDto(Long tagId, String tagName, int articleCount, int likeCount, boolean isSubscribed) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.articleCount = articleCount;
        this.likeCount = likeCount;
        this.isSubscribed = isSubscribed;
    }

    private ResponseTagDto(UserTag userTag) { // 유저가 구독한 태그 조회 시 사용
        this.tagId = userTag.getTag().getId();
        this.tagName = userTag.getTag().getTagName();
        this.articleCount = userTag.getTag().getTotalLikeCount();
        this.likeCount = userTag.getTag().getArticleTags().size();
        this.isSubscribed = true;
    }

    public static ResponseTagDto from(UserTag userTag) {
        return new ResponseTagDto(userTag);
    }

    public static ResponseTagDto of(Long tagId, String tagName, int articleCount, int likeCount, boolean isSubscribed) {
        return new ResponseTagDto(tagId, tagName, articleCount, likeCount, isSubscribed);
    }
}
