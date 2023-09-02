package com.spring.guideance.user.controller;

import com.spring.guideance.post.dto.response.ResponseCommentDto;
import com.spring.guideance.post.dto.response.ResponseSimpleArticleDto;
import com.spring.guideance.tag.dto.ResponseTagDto;
import com.spring.guideance.user.dto.request.UpdateUserDto;
import com.spring.guideance.user.dto.response.ResponseNoticeDto;
import com.spring.guideance.user.dto.response.ResponseUserDto;
import com.spring.guideance.user.service.NoticeService;
import com.spring.guideance.user.service.UserService;
import com.spring.guideance.util.SizeUtil;
import com.spring.guideance.util.api.ApiResponse;
import com.spring.guideance.util.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final NoticeService noticeService;

    // 회원정보 조회
    @GetMapping
    public ApiResponse<ResponseUserDto> getUser(@RequestHeader("userId") Long userId) {
        return ApiResponse.success(userService.getUser(userId), ResponseCode.USER_FOUND.getMessage());
    }

    // 회원정보 수정
    @PutMapping("/update")
    public ApiResponse<Void> updateUser(@RequestHeader("userId") Long userId, @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUser(userId, updateUserDto.getUserName());
        return ApiResponse.success(null, ResponseCode.USER_UPDATED.getMessage());
    }

    // 회원정보 삭제
    @DeleteMapping("/delete")
    public ApiResponse<Void> deleteUser(@RequestHeader("userId") Long userId) {
        userService.deleteUser(userId);
        return ApiResponse.success(null, ResponseCode.USER_DELETED.getMessage());
    }

    // 작성한 게시물 조회 (페이징)
    @GetMapping("/articles/writes")
    public ApiResponse<Page<ResponseSimpleArticleDto>> getArticles(@RequestHeader("userId") Long userId, @PageableDefault(size = SizeUtil.SIZE.PAGE_MEDIUM) Pageable pageable) {
        return ApiResponse.success(userService.getUserArticles(userId, pageable), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 작성한 댓글 조회 (페이징)
    @GetMapping("/comments")
    public ApiResponse<Page<ResponseCommentDto>> getComments(@RequestHeader("userId") Long userId, @PageableDefault(size = SizeUtil.SIZE.PAGE_MEDIUM) Pageable pageable) {
        return ApiResponse.success(userService.getUserComments(userId, pageable), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 구독한 태그 조회
    @GetMapping("/tags")
    public ApiResponse<List<ResponseTagDto>> getTags(@RequestHeader("userId") Long userId) {
        return ApiResponse.success(userService.getUserTags(userId), ResponseCode.TAG_FOUND.getMessage());
    }

    // 수신한 알림 조회 (페이징)
    @GetMapping("/notices")
    public ApiResponse<Page<ResponseNoticeDto>> getNotices(@RequestHeader("userId") Long userId, @PageableDefault(size = SizeUtil.SIZE.PAGE_MEDIUM) Pageable pageable) {
        return ApiResponse.success(userService.getUserNotices(userId, pageable), ResponseCode.NOTICE_FOUND.getMessage());
    }

    // 좋아요 누른 게시물 조회 (페이징)
    @GetMapping("/articles/likes")
    public ApiResponse<Page<ResponseSimpleArticleDto>> getLikes(@RequestHeader("userId") Long userId, @PageableDefault(size = SizeUtil.SIZE.PAGE_MEDIUM) Pageable pageable) {
        return ApiResponse.success(userService.getUserLikesArticles(userId, pageable), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 유저가 특정 알림을 읽음
    @PostMapping("/userNotices/{noticeId}/read")
    public ApiResponse<Void> readNotice(@RequestHeader("userId") Long userId, @PathVariable Long noticeId) {
        noticeService.readUserNotice(userId, noticeId);
        return ApiResponse.success(null, ResponseCode.NOTICE_READ.getMessage());
    }

    // 유저가 특정 알림을 삭제
    @DeleteMapping("/userNotices/{noticeId}/delete")
    public ApiResponse<Void> deleteNotice(@RequestHeader("userId") Long userId, @PathVariable Long noticeId) {
        noticeService.deleteUserNotice(userId, noticeId);
        return ApiResponse.success(null, ResponseCode.NOTICE_DELETED.getMessage());
    }
}
