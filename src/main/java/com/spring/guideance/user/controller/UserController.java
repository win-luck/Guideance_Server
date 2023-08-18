package com.spring.guideance.user.controller;

import com.spring.guideance.post.dto.response.ResponseCommentDto;
import com.spring.guideance.post.dto.response.ResponseSimpleArticleDto;
import com.spring.guideance.tag.dto.ResponseTagDto;
import com.spring.guideance.user.dto.request.CreateUserDto;
import com.spring.guideance.user.dto.request.LoginUserDto;
import com.spring.guideance.user.dto.request.UpdateUserDto;
import com.spring.guideance.user.dto.response.ResponseNoticeDto;
import com.spring.guideance.user.dto.response.ResponseUserDto;
import com.spring.guideance.user.service.NoticeService;
import com.spring.guideance.user.service.UserService;
import com.spring.guideance.util.api.ApiResponse;
import com.spring.guideance.util.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final NoticeService noticeService;

    // 회원가입
    @PostMapping("/create")
    public ApiResponse<Long> createUser(@RequestBody CreateUserDto createUserDto) {
        return ApiResponse.success(userService.createUser(createUserDto), ResponseCode.USER_CREATED.getMessage());
    }

    // 로그인
    @PostMapping("/login")
    public ApiResponse<ResponseUserDto> login(@RequestBody LoginUserDto loginUserDto) {
        return ApiResponse.success(userService.login(loginUserDto.getEmail()), ResponseCode.USER_LOGINED.getMessage());
    }

    // 회원정보 조회
    @GetMapping("/{userId}")
    public ApiResponse<ResponseUserDto> getUser(@PathVariable Long userId) {
        return ApiResponse.success(userService.getUser(userId), ResponseCode.USER_FOUND.getMessage());
    }

    // 회원정보 수정
    @PutMapping("/{userId}/update")
    public ApiResponse<Void> updateUser(@PathVariable Long userId, UpdateUserDto updateUserDto) {
        // 이미지 업로드 관련 S3 환경 추후 추가
        userService.updateUser(updateUserDto, null);
        return ApiResponse.success(null, ResponseCode.USER_UPDATED.getMessage());
    }

    // 회원정보 삭제
    @DeleteMapping("/{userId}/delete")
    public ApiResponse<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ApiResponse.success(null, ResponseCode.USER_DELETED.getMessage());
    }

    // 작성한 게시물 조회 (페이징)
    @GetMapping("/{userId}/articles/writes")
    public ApiResponse<Page<ResponseSimpleArticleDto>> getArticles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @PathVariable Long userId) {
        return ApiResponse.success(userService.getUserArticles(userId, PageRequest.of(page, size)), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 작성한 댓글 조회 (페이징)
    @GetMapping("/{userId}/comments")
    public ApiResponse<Page<ResponseCommentDto>> getComments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @PathVariable Long userId) {
        return ApiResponse.success(userService.getUserComments(userId, PageRequest.of(page, size)), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 구독한 태그 조회
    @GetMapping("/{userId}/tags")
    public ApiResponse<List<ResponseTagDto>> getTags(@PathVariable Long userId) {
        return ApiResponse.success(userService.getUserTags(userId), ResponseCode.TAG_FOUND.getMessage());
    }

    // 수신한 알림 조회 (페이징)
    @GetMapping("/{userId}/notices")
    public ApiResponse<Page<ResponseNoticeDto>> getNotices(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @PathVariable Long userId) {
        return ApiResponse.success(userService.getUserNotices(userId, PageRequest.of(page, size)), ResponseCode.NOTICE_FOUND.getMessage());
    }

    // 좋아요 누른 게시물 조회 (페이징)
    @GetMapping("/{userId}/articles/likes")
    public ApiResponse<Page<ResponseSimpleArticleDto>> getLikes(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @PathVariable Long userId) {
        return ApiResponse.success(userService.getUserLikesArticles(userId, PageRequest.of(page, size)), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 유저가 특정 알림을 읽음
    @PostMapping("/{userId}/notices/{noticeId}/read")
    public ApiResponse<Void> readNotice(@PathVariable Long userId, @PathVariable Long noticeId) {
        noticeService.readUserNotice(noticeId);
        return ApiResponse.success(null, ResponseCode.NOTICE_READ.getMessage());
    }

    // 유저가 특정 알림을 삭제
    @DeleteMapping("/{userId}/notices/{noticeId}/delete")
    public ApiResponse<Void> deleteNotice(@PathVariable Long userId, @PathVariable Long noticeId) {
        noticeService.deleteUserNotice(noticeId);
        return ApiResponse.success(null, ResponseCode.NOTICE_DELETED.getMessage());
    }

}
