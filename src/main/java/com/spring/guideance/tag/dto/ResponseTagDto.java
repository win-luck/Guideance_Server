package com.spring.guideance.tag.dto;

import com.spring.guideance.tag.domain.UserTag;
import lombok.Data;

@Data
public class ResponseTagDto {
    private Long tagId;
    private String tagName;
    private int articleCount;
    private int likeCount;
    private boolean isSubscribed;

    private ResponseTagDto(Long tagId, String tagName, int articleCount, int likeCount) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.articleCount = articleCount;
        this.likeCount = likeCount;
    }

    private ResponseTagDto(UserTag userTag) {
        this.tagId = userTag.getTag().getId();
        this.tagName = userTag.getTag().getTagName();
        this.articleCount = userTag.getTag().getTotalLikeCount();
        this.likeCount = userTag.getTag().getArticleTags().size();
    }

    public void setSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public static ResponseTagDto from(UserTag userTag) {
        return new ResponseTagDto(userTag);
    }

    public static ResponseTagDto of(Long tagId, String tagName, int articleCount, int likeCount) {
        return new ResponseTagDto(tagId, tagName, articleCount, likeCount);
    }
}
