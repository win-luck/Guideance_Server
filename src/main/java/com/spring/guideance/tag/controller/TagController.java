package com.spring.guideance.tag.controller;

import com.spring.guideance.post.dto.response.ResponseSimpleArticleDto;
import com.spring.guideance.tag.dto.CreateTagDto;
import com.spring.guideance.tag.dto.ResponseTagDto;
import com.spring.guideance.tag.service.TagService;
import com.spring.guideance.user.dto.response.ResponseUserDto;
import com.spring.guideance.util.SizeUtil;
import com.spring.guideance.util.api.ApiResponse;
import com.spring.guideance.util.exception.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "4. Tag")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;

    // 게시물 업로드 상황에서 새 태그 생성 -> 역할 분리를 위해 ArticleController에서 담당

    // 새 태그 생성
    @Operation(summary = "새 태그 생성", description = "새 태그를 생성합니다.")
    @PostMapping("/create")
    public ApiResponse<Long> createTag(@RequestBody CreateTagDto createTagDto) {
        return ApiResponse.success(tagService.createTag(createTagDto.getTagName()), ResponseCode.TAG_CREATED.getMessage());
    }

    // 태그 삭제
    @Operation(summary = "태그 삭제", description = "태그를 삭제합니다.")
    @DeleteMapping("/{tagId}")
    public ApiResponse<Void> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return ApiResponse.success(null, ResponseCode.TAG_DELETED.getMessage());
    }

    // 태그 검색 결과 조회 (페이징)
    @Operation(summary = "태그 검색 결과 조회", description = "태그 검색 결과를 페이징으로 조회합니다.")
    @GetMapping("/search/{tagName}")
    public ApiResponse<Page<ResponseTagDto>> searchTag(@RequestHeader("userId") Long userId, @PathVariable String tagName, @PageableDefault(size = SizeUtil.SIZE.PAGE_MEDIUM) Pageable pageable) {
        return ApiResponse.success(tagService.searchTag(userId, tagName, pageable), ResponseCode.TAG_FOUND.getMessage());
    }

    // 태그 목록 조회 (태그명, 게시물수, 좋아요수) - 유저가 구독한 태그가 앞에 오도록, 가장 최신으로 구독한 태그가 앞에 오도록 정렬 (페이징)
    @Operation(summary = "태그 목록 조회", description = "태그 목록을 페이징으로 조회합니다. 유저가 구독한 태그가 앞에 오도록, 가장 최신으로 구독한 태그가 앞에 오도록 정렬합니다.")
    @GetMapping("/list")
    public ApiResponse<Page<ResponseTagDto>> getTagList(@RequestHeader("userId") Long userId, @PageableDefault(size = SizeUtil.SIZE.PAGE_MEDIUM) Pageable pageable) {
        return ApiResponse.success(tagService.getTagList(userId, pageable), ResponseCode.TAG_FOUND.getMessage());
    }

    // 유저가 태그를 구독
    @Operation(summary = "유저가 태그를 구독", description = "유저가 태그를 구독합니다.")
    @PostMapping("{tagId}/subscribe")
    public ApiResponse<Void> subscribeTag(@RequestHeader("userId") Long userId, @PathVariable Long tagId) {
        tagService.subscribeTag(userId, tagId);
        return ApiResponse.success(null, ResponseCode.TAG_SUBSCRIBED.getMessage());
    }

    // 유저가 태그 구독 취소
    @Operation(summary = "유저가 태그 구독 취소", description = "유저가 태그 구독을 취소합니다.")
    @PostMapping("{tagId}/subscribe/cancel")
    public ApiResponse<Void> unsubscribeTag(@RequestHeader("userId") Long userId, @PathVariable Long tagId) {
        tagService.unsubscribeTag(userId, tagId);
        return ApiResponse.success(null, ResponseCode.TAG_UNSUBSCRIBED.getMessage());
    }

    // 특정 태그가 포함된 게시물 목록 조회 (관리자용)
    @Operation(summary = "특정 태그가 포함된 게시물 목록 조회", description = "특정 태그가 포함된 게시물 목록을 조회합니다.")
    @GetMapping("/{tagId}/articles")
    public ApiResponse<List<ResponseSimpleArticleDto>> getArticleListByTag(@PathVariable Long tagId) {
        return ApiResponse.success(tagService.getArticleListByTag(tagId), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 특정 태그를 구독하는 유저 목록 조회 (관리자용)
    @Operation(summary = "특정 태그를 구독하는 유저 목록 조회", description = "특정 태그를 구독하는 유저 목록을 조회합니다.")
    @GetMapping("/{tagId}/users")
    public ApiResponse<List<ResponseUserDto>> getUserListByTag(@PathVariable Long tagId) {
        return ApiResponse.success(tagService.getUserListByTag(tagId), ResponseCode.USER_FOUND.getMessage());
    }
}
