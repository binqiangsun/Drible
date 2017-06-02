package com.lukou.service.http;


/**
 * Created by sunbinqiang on 9/1/16.
 */

public class HttpResult<T> {
    public int code;
    public String msg;
    public T data;


    public boolean isSuccess() {
        return code == HttpConstant.SUCCESS;
    }

    public boolean isError() {
        return code == HttpConstant.ERROR;
    }
}
