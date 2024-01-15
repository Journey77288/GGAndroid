package io.ganguo.utils.exception;

import io.ganguo.utils.util.Strings;

/**
 * Exception - 基类
 * <p/>
 * Created by Tony on 9/30/15.
 */
public class BaseException extends RuntimeException {
    private int code;
    private String message;

    /**
     * Constructs a new {@code Exception} that includes the current stack trace.
     */
    public BaseException() {
    }

    /**
     * Constructs a new {@code Exception} that includes the current stack trace.
     * specified detail message.
     * specified detail code.
     *
     * @param code    the detail code for this exception.
     * @param message the detail message for this exception.
     */
    public BaseException(String message, int code) {
        super(message);
        this.code = code;
    }

    /**
     * Constructs a new {@code Exception} that includes the current stack trace.
     * specified detail message.
     * specified detail code.
     * specified detail Throwable.
     *
     * @param code    the detail code for this exception.
     * @param message the detail message for this exception.
     * @param cause   the detail cause for this exception.
     */
    public BaseException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    /**
     * Constructs a new {@code Exception} that includes the current stack trace.
     * specified detail message.
     * specified detail Throwable.
     *
     * @param code  the detail code for this exception.
     * @param cause the detail cause for this exception.
     */
    public BaseException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified detail message.
     *
     * @param detailMessage the detail message for this exception.
     */
    public BaseException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace, the
     * specified detail message and the specified cause.
     *
     * @param detailMessage the detail message for this exception.
     * @param throwable
     */
    public BaseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified cause.
     *
     * @param throwable the cause of this exception.
     */
    public BaseException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified message.
     *
     * @param message the code of this exception.
     */
    public <S extends BaseException> S setMessage(String message) {
        this.message = message;
        return (S) this;
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified code.
     *
     * @param code the code of this exception.
     */
    public <S extends BaseException> S setCode(int code) {
        this.code = code;
        return (S) this;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        if (Strings.isNotEmpty(message)) {
            return message;
        }
        return super.getMessage();
    }


}
