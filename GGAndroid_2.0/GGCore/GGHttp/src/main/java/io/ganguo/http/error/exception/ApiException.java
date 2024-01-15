package io.ganguo.http.error.exception;

import io.ganguo.utils.exception.BaseException;

/**
 * 常见API错误
 * Created by leo on 2018/6/20.
 */
public class ApiException extends BaseException {
    public ApiException(String message, int code) {
        super(message, code);
    }

    public ApiException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }
}
