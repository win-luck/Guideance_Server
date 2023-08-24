package com.spring.guideance.auth.dto;

import com.spring.guideance.auth.dto.KakaoAccount;
import lombok.Data;

@Data
public class KakaoUserInfoResponse {
    private Long id;
    private boolean has_signed_up;
    private KakaoAccount kakao_account;
}
