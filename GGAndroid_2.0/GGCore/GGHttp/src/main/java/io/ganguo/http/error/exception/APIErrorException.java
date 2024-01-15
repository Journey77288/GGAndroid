package io.ganguo.http.error.exception;


import io.ganguo.utils.exception.BaseException;

/**
 * 常见API错误
 * Created by leo on 2018/6/20.
 */
public class APIErrorException extends BaseException {
    public APIErrorException() {
    }

    public APIErrorException(String message, int code) {
        super(message, code);
    }

    public APIErrorException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public APIErrorException(Throwable cause, int code) {
        super(cause, code);
    }

    public APIErrorException(String detailMessage) {
        super(detailMessage);
    }

    public APIErrorException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public APIErrorException(Throwable throwable) {
        super(throwable);
    }
}
