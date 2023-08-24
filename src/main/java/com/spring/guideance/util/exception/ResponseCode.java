package com.spring.guideance.util.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ResponseCode {

    // 400 Bad Request
    BAD_REQUEST(HttpStatus.BAD_REQUEST, false, "잘못된 요청입니다."),
    NOTICE_TYPE_WRONG(HttpStatus.BAD_REQUEST, false, "잘못된 알림 타입입니다."),

    // 403 Forbidden
    FORBIDDEN(HttpStatus.FORBIDDEN, false, "권한이 없습니다."),

    // 404 Not Found
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, false, "사용자를 찾을 수 없습니다."),
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, false, "알림을 찾을 수 없습니다."),
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, false, "게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, false, "댓글을 찾을 수 없습니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, false, "좋아요를 찾을 수 없습니다."),
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, false, "태그를 찾을 수 없습니다."),

    // 405 Method Not Allowed
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, false, "허용되지 않은 메소드입니다."),

    // 409 Conflict
    UNSUBSCRIBE_TAG(HttpStatus.BAD_REQUEST, false, "이미 구독하지 않은 태그입니다."),
    TAG_ALREADY_SUBSCRIBED(HttpStatus.CONFLICT, false, "이미 구독한 태그입니다."),
    TAG_ALREADY_EXISTS(HttpStatus.CONFLICT, false, "이미 존재하는 태그입니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, false, "이미 존재하는 사용자입니다."),
    LIKE_ALREADY_EXISTS(HttpStatus.CONFLICT, false, "이미 좋아요를 누른 게시글입니다."),
    LIKE_ALREADY_CANCELED(HttpStatus.CONFLICT, false, "이미 좋아요를 취소한 게시글입니다."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "서버에 오류가 발생하였습니다."),

    // 200 OK
    USER_LOGINED(HttpStatus.OK, true, "로그인 되었습니다."),
    USER_FOUND(HttpStatus.OK, true, "사용자를 조회하였습니다."),
    TAG_FOUND(HttpStatus.OK, true, "태그를 조회하였습니다."),
    ARTICLE_FOUND(HttpStatus.OK, true, "게시글을 조회하였습니다."),
    COMMENT_FOUND(HttpStatus.OK, true, "댓글을 조회하였습니다."),
    LIKE_FOUND(HttpStatus.OK, true, "좋아요를 조회하였습니다."),
    NOTICE_FOUND(HttpStatus.OK, true, "알림을 조회하였습니다."),

    // 201 Created
    USER_CREATED(HttpStatus.CREATED, true, "사용자가 생성되었습니다."),
    TAG_CREATED(HttpStatus.CREATED, true, "태그가 생성되었습니다."),
    ARTICLE_CREATED(HttpStatus.CREATED, true, "게시글이 생성되었습니다."),
    COMMENT_CREATED(HttpStatus.CREATED, true, "댓글이 생성되었습니다."),
    LIKE_CREATED(HttpStatus.CREATED, true, "좋아요가 생성되었습니다."),
    NOTICE_CREATED(HttpStatus.CREATED, true, "알림이 생성되었습니다."),

    // 204 No Content
    TAG_SUBSCRIBED(HttpStatus.CREATED, true, "태그 구독이 완료되었습니다."),
    TAG_UNSUBSCRIBED(HttpStatus.CREATED, true, "태그 구독이 취소되었습니다."),
    NOTICE_READ(HttpStatus.CREATED, true, "알림을 읽었습니다."),
    USER_UPDATED(HttpStatus.CREATED, true, "사용자 정보가 수정되었습니다."),
    ARTICLE_UPDATED(HttpStatus.CREATED, true, "게시글이 수정되었습니다."),
    COMMENT_UPDATED(HttpStatus.CREATED, true, "댓글이 수정되었습니다."),
    TAG_UPDATED(HttpStatus.CREATED, true, "태그가 수정되었습니다."),
    ARTICLE_DELETED(HttpStatus.NO_CONTENT, true, "게시글이 삭제되었습니다."),
    COMMENT_DELETED(HttpStatus.NO_CONTENT, true, "댓글이 삭제되었습니다."),
    LIKE_DELETED(HttpStatus.NO_CONTENT, true, "좋아요가 취소되었습니다."),
    NOTICE_DELETED(HttpStatus.NO_CONTENT, true, "알림이 삭제되었습니다."),
    TAG_DELETED(HttpStatus.NO_CONTENT, true, "태그가 삭제되었습니다."),
    USER_DELETED(HttpStatus.NO_CONTENT, true, "사용자 정보가 삭제되었습니다."),
    USER_LOGOUT(HttpStatus.NO_CONTENT, true, "사용자가 로그아웃 되었습니다.");

    private final HttpStatus httpStatus;
    private final Boolean success;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }

}
