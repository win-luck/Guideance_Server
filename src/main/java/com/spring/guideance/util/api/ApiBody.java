package com.spring.guideance.util.api;

public class ApiBody<T> {
    private final T data;
    private final T msg;

    public ApiBody(T data, T msg) {
        this.data = data;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public T getMsg() {
        return msg;
    }
}
