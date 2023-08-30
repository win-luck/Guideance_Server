package com.spring.guideance.post.controller;

import com.spring.guideance.post.dto.request.*;
import com.spring.guideance.post.dto.response.ResponseArticleDto;
import com.spring.guideance.post.dto.response.ResponseSimpleArticleDto;
import com.spring.guideance.post.service.ArticleService;
import com.spring.guideance.tag.service.TagService;
import com.spring.guideance.user.service.NoticeService;
import com.spring.guideance.util.api.ApiResponse;
import com.spring.guideance.util.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final TagService tagService;
    private final NoticeService noticeService;

    // 게시물 목록 조회 (제목, 내용, 작성자, 좋아요 수, 댓글 수) (페이징)
    @GetMapping
    public ApiResponse<Page<ResponseSimpleArticleDto>> getArticles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam Long userId) {
        return ApiResponse.success(articleService.getArticles(PageRequest.of(page, size), userId), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 특정 게시물 정보 조회 (제목, 내용, 댓글 목록, 좋아요 목록, 작성자, 작성일시)
    @GetMapping("/{articleId}")
    public ApiResponse<ResponseArticleDto> getSingleArticle(@PathVariable Long articleId, @RequestParam Long userId) {
        return ApiResponse.success(articleService.getSingleArticle(articleId, userId), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 게시물 검색 결과 조회 (페이징)
    @GetMapping("/search/{articleName}")
    public ApiResponse<Page<ResponseSimpleArticleDto>> searchArticles(@PathVariable String articleName, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam Long userId) {
        return ApiResponse.success(articleService.searchArticles(articleName, PageRequest.of(page, size), userId), ResponseCode.ARTICLE_FOUND.getMessage());
    }

    // 게시물 작성
    @PostMapping
    public ApiResponse<Long> createArticle(@RequestBody CreateArticleDto articleDto) {
        Long articleId = articleService.createArticle(articleDto);
        tagService.addTagToArticle(articleId, articleDto.getTags());
        noticeService.sendNoticeForNewArticle(articleId); // 게시물이 가진 태그들을 구독하는 사람들에게 알림전송
        return ApiResponse.success(articleId, ResponseCode.ARTICLE_CREATED.getMessage());
    }

    // 게시물 수정
    @PutMapping("/{articleId}")
    public ApiResponse<Void> updateArticle(@PathVariable Long articleId, @RequestBody UpdateArticleDto articleDto) {
        articleService.updateArticle(articleId, articleDto);
        return ApiResponse.success(null, ResponseCode.ARTICLE_UPDATED.getMessage());
    }

    // 게시물 삭제
    @DeleteMapping("/{articleId}")
    public ApiResponse<Void> deleteArticle(@PathVariable Long articleId, @RequestBody DeleteArticleDto articleDto) {
        articleService.deleteArticle(articleId, articleDto);
        return ApiResponse.success(null, ResponseCode.ARTICLE_DELETED.getMessage());
    }

    // 게시물에 댓글 작성
    @PostMapping("/{articleId}/comment")
    public ApiResponse<Long> createComment(@PathVariable Long articleId, @RequestBody CreateCommentDto commentDto) {
        Long commentId = articleService.createComment(articleId, commentDto);
        noticeService.sendNoticeForComment(commentId); // 게시물 작성자에게 댓글 알림전송
        return ApiResponse.success(commentId, ResponseCode.COMMENT_CREATED.getMessage());
    }

    // 게시물에 댓글 수정
    @PutMapping("/comment/{commentId}")
    public ApiResponse<Void> updateComment(@PathVariable Long commentId, @RequestBody UpdateCommentDto commentDto) {
        articleService.updateComment(commentId, commentDto);
        return ApiResponse.success(null, ResponseCode.COMMENT_UPDATED.getMessage());
    }

    // 게시물에 댓글 삭제
    @DeleteMapping("/comment/{commentId}/delete")
    public ApiResponse<Void> deleteComment(@PathVariable Long commentId, @RequestBody DeleteCommentDto commentDto) {
        articleService.deleteComment(commentId, commentDto.getUserId());
        return ApiResponse.success(null, ResponseCode.COMMENT_DELETED.getMessage());
    }

    // 게시물에 좋아요
    @PostMapping("/{articleId}/like")
    public ApiResponse<Long> like(@PathVariable Long articleId, @RequestBody RequestLikeDto requestLikeDto) {
        Long likeId = articleService.createLikes(articleId, requestLikeDto);
        noticeService.sendNoticeForLike(likeId); // 게시물 작성자에게 좋아요 알림전송
        return ApiResponse.success(likeId, ResponseCode.LIKE_CREATED.getMessage());
    }

    // 게시물에 좋아요 취소
    @PostMapping("/{articleId}/like/cancel")
    public ApiResponse<Void> cancelLike(@PathVariable Long articleId, @RequestBody RequestLikeDto requestLikeDto) {
        articleService.deleteLikes(articleId, requestLikeDto);
        return ApiResponse.success(null, ResponseCode.LIKE_DELETED.getMessage());
    }

}
