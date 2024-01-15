package io.ganguo.http.error.exception;

/**
 * <p>
 * Token过期/错误
 * </p>
 * Created by leo on 2018/8/4.
 */

public class UnAuthorizedException extends ApiException {
    public UnAuthorizedException(String message, int code) {
        super(message, code);
    }

    public UnAuthorizedException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }
}
