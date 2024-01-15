package io.ganguo.http.entity.response;

/**
 * <p>
 * BHttpResponse - 基类
 * 注：用于解析判断一些服务器状态
 * </p>
 * Created by leo on 2018/7/17.
 */
public class BHttpResponse {
    private int code;
    private String status;
    private String message;


    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public BHttpResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public BHttpResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public BHttpResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "BHttpResponse{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
