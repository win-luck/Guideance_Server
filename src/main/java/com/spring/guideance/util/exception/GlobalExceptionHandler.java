package com.spring.guideance.util.exception;

import com.spring.guideance.util.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArticleException.class)
    public ApiResponse<Void> handleArticleException(ArticleException e) {
        log.info("ArticleException: {}", e.getMessage());
        return ApiResponse.fail(e.getResponseCode());
    }

    @ExceptionHandler(UserException.class)
    public ApiResponse<Void> handleUserException(UserException e) {
        log.info("UserException: {}", e.getMessage());
        return ApiResponse.fail(e.getResponseCode());
    }

    @ExceptionHandler(NoticeException.class)
    public ApiResponse<Void> handleNoticeException(NoticeException e) {
        log.info("NoticeException: {}", e.getMessage());
        return ApiResponse.fail(e.getResponseCode());
    }

    @ExceptionHandler(TagException.class)
    public ApiResponse<Void> handleTagException(TagException e) {
        log.info("TagException: {}", e.getMessage());
        return ApiResponse.fail(e.getResponseCode());
    }
}
