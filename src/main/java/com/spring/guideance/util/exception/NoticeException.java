package com.spring.guideance.util.exception;

public class NoticeException extends BaseException{
    public NoticeException(ResponseCode responseCode) {
        super(responseCode);
    }
}
