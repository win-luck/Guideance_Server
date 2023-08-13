package com.spring.guideance.util.exception;

public class ArticleException extends BaseException{
    public ArticleException(ResponseCode responseCode) {
        super(responseCode);
    }
}
