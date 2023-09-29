package com.spring.guideance.auth.controller;

import com.spring.guideance.auth.component.KakaoUserInfo;
import com.spring.guideance.auth.dto.KakaoUserInfoResponse;
import com.spring.guideance.user.dto.request.CreateUserDto;
import com.spring.guideance.user.dto.response.ResponseUserDto;
import com.spring.guideance.user.service.UserService;
import com.spring.guideance.util.api.ApiResponse;
import com.spring.guideance.util.exception.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "1. Auth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final KakaoUserInfo kakaoUserInfo;

    @Operation(summary = "카카오 로그인", description = "카카오 소셜로그인을 진행합니다.")
    @PostMapping
    @ResponseBody
    public ApiResponse<ResponseUserDto> kakaoOauth(@RequestParam("token") String accessToken) {
        // 클라이언트는 서버로 accessToken을 보내고 서버는 accessToken을 이용하여 사용자의 정보를 가져온다.
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(accessToken);
        log.info("회원 정보 입니다.{}", userInfo);
        if (userService.isAlreadyUser(userInfo.getId().toString())) {
            log.info("이미 가입된 회원이므로 회원가입 없이 바로 로그인합니다.");
            return ApiResponse.success(userService.getUserByKeyCode(userInfo.getId().toString()), ResponseCode.USER_LOGINED.getMessage());
        } else {
            log.info("회원가입을 진행합니다.");
            userService.createUser(new CreateUserDto(userInfo.getKakaoAccount().getProfile().getNickname(), userInfo.getId().toString(), userInfo.getKakaoAccount().getProfile().getProfileImageUrl()));
            return ApiResponse.success(userService.getUserByKeyCode(userInfo.getId().toString()), ResponseCode.USER_CREATED.getMessage());
        }
    }
}
