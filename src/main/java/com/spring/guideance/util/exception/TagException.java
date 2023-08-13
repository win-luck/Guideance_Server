package com.spring.guideance.util.exception;

public class TagException extends BaseException{
    public TagException(ResponseCode responseCode) {
        super(responseCode);
    }
}
