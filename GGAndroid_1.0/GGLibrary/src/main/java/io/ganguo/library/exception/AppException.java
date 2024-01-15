package io.ganguo.library.exception;

/**
 * Created by zhihui_chen on 14-8-14.
 */
public class AppException extends Exception {
    public AppException() {
        super();
    }

    public AppException(String detailMessage) {
        super(detailMessage);
    }
}
