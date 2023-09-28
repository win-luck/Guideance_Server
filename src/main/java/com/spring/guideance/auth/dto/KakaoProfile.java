package com.spring.guideance.auth.dto;

import lombok.Data;

@Data
public class KakaoProfile {

    private String nickname;
    private String profileImageUrl;
    private String thumbnailImageUrl;
    private boolean isDefaultImage;
}
