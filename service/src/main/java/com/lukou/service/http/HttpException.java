package com.lukou.service.http;

/**
 * Created by sunbinqiang on 9/1/16.
 */

public class HttpException extends RuntimeException {
    public int code;
    public String message;

    public HttpException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
