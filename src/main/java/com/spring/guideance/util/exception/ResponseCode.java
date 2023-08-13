package com.spring.guideance.util.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access =  AccessLevel.PRIVATE)
@Getter
public enum ResponseCode {
    // 400 Bad Request
    BAD_REQUEST(HttpStatus.BAD_REQUEST, false, "잘못된 요청입니다.");



    private final HttpStatus httpStatus;
    private final Boolean success;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }

}
