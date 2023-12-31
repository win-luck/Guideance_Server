package com.spring.guideance.post.controller;

import com.spring.guideance.post.dto.request.*;
import com.spring.guideance.post.dto.response.ResponseArticleDto;
import com.spring.guideance.post.dto.response.ResponseSimpleArticleDto;
import com.spring.guideance.post.service.ArticleService;
import com.spring.guideance.tag.service.TagService;
import com.spring.guideance.user.service.NoticeService;
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

@Api(tags = "2. Article")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;
    private final TagService tagService;
    private final NoticeService noticeService;

    // 게시물 목록 조회 (제목, 내용, 작성자, 좋아요 수, 댓글 수) (페이징)
    @Operation(summary = "게시물 목록 조회", description = "게시물 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<Page<ResponseSimpleArticleDto>> getArticles(@RequestHeader("userId") Long userId, @PageableDefault(size = SizeUtil.SIZE.PAGE_MEDIUM) Pageable pageable) {
        return ApiResponse.success(articleService.getArticles(pageable, userId), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 특정 게시물 정보 조회 (제목, 내용, 댓글 목록, 좋아요 목록, 작성자, 작성일시)
    @Operation(summary = "특정 게시물 정보 조회", description = "특정 게시물 정보를 조회합니다.")
    @GetMapping("/{articleId}")
    public ApiResponse<ResponseArticleDto> getSingleArticle(@RequestHeader("userId") Long userId, @PathVariable Long articleId) {
        return ApiResponse.success(articleService.getSingleArticle(articleId, userId), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 게시물 검색 결과 조회 (페이징)
    @Operation(summary = "게시물 검색 결과 조회", description = "게시물 검색 결과를 조회합니다.")
    @GetMapping("/search/{articleName}")
    public ApiResponse<Page<ResponseSimpleArticleDto>> searchArticles(@RequestHeader("userId") Long userId, @PathVariable String articleName, @PageableDefault(size = SizeUtil.SIZE.PAGE_MEDIUM) Pageable pageable) {
        return ApiResponse.success(articleService.searchArticles(articleName, pageable, userId), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 게시물 작성
    @Operation(summary = "게시물 작성", description = "게시물을 작성합니다.")
    @PostMapping
    public ApiResponse<Long> createArticle(@RequestHeader("userId") Long userId, @RequestBody CreateArticleDto articleDto) {
        Long articleId = articleService.createArticle(articleDto, userId);
        tagService.addTagToArticle(articleId, articleDto.getTags());
        noticeService.sendNoticeForNewArticle(articleId); // 게시물이 가진 태그들을 구독하는 사람들에게 알림전송
        return ApiResponse.success(articleId, ResponseCode.ARTICLE_CREATED.getMessage());
    }

    // 게시물 수정
    @Operation(summary = "게시물 수정", description = "게시물을 수정합니다.")
    @PutMapping("/{articleId}")
    public ApiResponse<Void> updateArticle(@RequestHeader("userId") Long userId, @PathVariable Long articleId, @RequestBody UpdateArticleDto articleDto) {
        articleService.updateArticle(articleId, articleDto, userId);
        return ApiResponse.success(null, ResponseCode.ARTICLE_UPDATED.getMessage());
    }

    // 게시물 삭제
    @Operation(summary = "게시물 삭제", description = "게시물을 삭제합니다.")
    @DeleteMapping("/{articleId}")
    public ApiResponse<Void> deleteArticle(@RequestHeader("userId") Long userId, @PathVariable Long articleId) {
        articleService.deleteArticle(articleId, userId);
        return ApiResponse.success(null, ResponseCode.ARTICLE_DELETED.getMessage());
    }

    // 게시물에 댓글 작성
    @Operation(summary = "게시물에 댓글 작성", description = "게시물에 댓글을 작성합니다.")
    @PostMapping("/{articleId}/comment")
    public ApiResponse<Long> createComment(@RequestHeader("userId") Long userId, @PathVariable Long articleId, @RequestBody CreateCommentDto commentDto) {
        Long commentId = articleService.createComment(articleId, commentDto, userId);
        noticeService.sendNoticeForComment(commentId); // 게시물 작성자에게 댓글 알림전송
        return ApiResponse.success(commentId, ResponseCode.COMMENT_CREATED.getMessage());
    }

    // 게시물에 댓글 수정
    @Operation(summary = "게시물에 댓글 수정", description = "게시물에 댓글을 수정합니다.")
    @PutMapping("/comment/{commentId}")
    public ApiResponse<Void> updateComment(@RequestHeader("userId") Long userId, @PathVariable Long commentId, @RequestBody UpdateCommentDto commentDto) {
        articleService.updateComment(commentId, commentDto, userId);
        return ApiResponse.success(null, ResponseCode.COMMENT_UPDATED.getMessage());
    }

    // 게시물에 댓글 삭제
    @Operation(summary = "게시물에 댓글 삭제", description = "게시물에 댓글을 삭제합니다.")
    @DeleteMapping("/comment/{commentId}/delete")
    public ApiResponse<Void> deleteComment(@RequestHeader("userId") Long userId, @PathVariable Long commentId) {
        articleService.deleteComment(commentId, userId);
        return ApiResponse.success(null, ResponseCode.COMMENT_DELETED.getMessage());
    }

    // 게시물에 좋아요
    @Operation(summary = "게시물에 좋아요", description = "게시물에 좋아요를 누릅니다.")
    @PostMapping("/{articleId}/like")
    public ApiResponse<Long> like(@RequestHeader("userId") Long userId, @PathVariable Long articleId) {
        Long likeId = articleService.createLikes(articleId, userId);
        noticeService.sendNoticeForLike(likeId); // 게시물 작성자에게 좋아요 알림전송
        return ApiResponse.success(likeId, ResponseCode.LIKE_CREATED.getMessage());
    }

    // 게시물에 좋아요 취소
    @Operation(summary = "게시물에 좋아요 취소", description = "게시물에 좋아요를 취소합니다.")
    @PostMapping("/{articleId}/like/cancel")
    public ApiResponse<Void> cancelLike(@RequestHeader("userId") Long userId, @PathVariable Long articleId) {
        articleService.deleteLikes(articleId, userId);
        return ApiResponse.success(null, ResponseCode.LIKE_DELETED.getMessage());
    }
}
