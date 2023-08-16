package com.spring.guideance.tag.controller;

import com.spring.guideance.post.dto.response.ResponseSimpleArticleDto;
import com.spring.guideance.tag.dto.CreateTagDto;
import com.spring.guideance.tag.dto.ResponseTagDto;
import com.spring.guideance.tag.service.TagService;
import com.spring.guideance.user.dto.response.ResponseUserDto;
import com.spring.guideance.util.api.ApiResponse;
import com.spring.guideance.util.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // 게시물 업로드 상황에서 새 태그 생성 기능은 역할 분리를 위해 ArticleController에서 처리

    // 새 태그 생성
    @PostMapping("/create")
    public ApiResponse<Long> createTag(@RequestBody CreateTagDto createTagDto) {
        return ApiResponse.success(tagService.createTag(createTagDto.getTagName()), ResponseCode.TAG_CREATED.getMessage());
    }

    // 태그 삭제
    @DeleteMapping("/{tagId}")
    public ApiResponse<Void> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return ApiResponse.success(null, ResponseCode.TAG_DELETED.getMessage());
    }

    // 태그 검색
    @GetMapping("/search/{tagName}")
    public ApiResponse<List<ResponseTagDto>> searchTag(@PathVariable String tagName) {
        return ApiResponse.success(tagService.searchTag(tagName), ResponseCode.TAG_FOUND.getMessage());
    }

    // 유저가 태그를 구독
    @PostMapping("{tagId}/user/{userId}")
    public ApiResponse<Void> subscribeTag(@PathVariable Long userId, @PathVariable Long tagId) {
        tagService.subscribeTag(userId, tagId);
        return ApiResponse.success(null, ResponseCode.TAG_SUBSCRIBED.getMessage());
    }

    // 유저가 태그 구독 취소
    @PostMapping("{tagId}/user/{userId}/cancel")
    public ApiResponse<Void> unsubscribeTag(@PathVariable Long userId, @PathVariable Long tagId) {
        tagService.unsubscribeTag(userId, tagId);
        return ApiResponse.success(null, ResponseCode.TAG_UNSUBSCRIBED.getMessage());
    }

    // 특정 태그가 포함된 게시물 목록 조회
    @GetMapping("/{tagId}/articles")
    public ApiResponse<List<ResponseSimpleArticleDto>> getArticleListByTag(@PathVariable Long tagId) {
        return ApiResponse.success(tagService.getArticleListByTag(tagId), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 특정 태그를 구독하는 유저 목록 조회
    @GetMapping("/{tagId}/users")
    public ApiResponse<List<ResponseUserDto>> getUserListByTag(@PathVariable Long tagId) {
        return ApiResponse.success(tagService.getUserListByTag(tagId), ResponseCode.USER_FOUND.getMessage());
    }

    // 전체 태그 목록 조회 (태그명, 게시물수, 좋아요수) - 유저가 구독한 태그가 앞에 오도록, 가장 최신으로 구독한 태그가 앞에 오도록 정렬
    @GetMapping("/list/{userId}")
    public ApiResponse<List<ResponseTagDto>> getTagList(@PathVariable Long userId) {
        return ApiResponse.success(tagService.getTagList(userId), ResponseCode.TAG_FOUND.getMessage());
    }

}
