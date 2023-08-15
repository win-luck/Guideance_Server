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
    public ResponseEntity<ApiResponse<Void>> handleArticleException(ArticleException e) {
        log.error("ArticleException: {}", e.getMessage());
        return new ResponseEntity<>(ApiResponse.fail(e.getResponseCode()), e.getResponseCode().getHttpStatus());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserException(UserException e) {
        log.error("UserException: {}", e.getMessage());
        return new ResponseEntity<>(ApiResponse.fail(e.getResponseCode()), e.getResponseCode().getHttpStatus());
    }

    @ExceptionHandler(NoticeException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoticeException(NoticeException e) {
        log.error("NoticeException: {}", e.getMessage());
        return new ResponseEntity<>(ApiResponse.fail(e.getResponseCode()), e.getResponseCode().getHttpStatus());
    }

    @ExceptionHandler(TagException.class)
    public ResponseEntity<ApiResponse<Void>> handleTagException(TagException e) {
        log.error("TagException: {}", e.getMessage());
        return new ResponseEntity<>(ApiResponse.fail(e.getResponseCode()), e.getResponseCode().getHttpStatus());
    }

}
