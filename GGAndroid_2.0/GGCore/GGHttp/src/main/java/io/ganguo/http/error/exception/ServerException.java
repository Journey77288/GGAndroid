package io.ganguo.http.error.exception;


/**
 * server exception. 40x 50x, etc
 * <p/>
 * Created by Wilson on 12/12/15.
 */
public class ServerException extends RuntimeException {
    private int code;

    public ServerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ServerException{" +
                "code=" + code +
                '}';
    }
}
