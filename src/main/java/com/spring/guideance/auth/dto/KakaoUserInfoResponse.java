package com.spring.guideance.auth.dto;

import lombok.Data;

@Data
public class KakaoUserInfoResponse {

    private Long id;
    private boolean hasSignedUp;
    private KakaoAccount kakaoAccount;
}
