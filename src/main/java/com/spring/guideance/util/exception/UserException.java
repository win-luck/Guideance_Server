package com.spring.guideance.util.exception;

public class UserException extends BaseException{
    public UserException(ResponseCode responseCode) {
        super(responseCode);
    }
}
