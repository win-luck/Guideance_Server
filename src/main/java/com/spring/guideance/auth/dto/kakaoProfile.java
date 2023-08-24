package com.spring.guideance.auth.dto;

import lombok.Data;

@Data
public class kakaoProfile {
    private String nickname;
    private String profile_image_url;
    private String thumbnail_image_url;
    private boolean is_default_image;
}
