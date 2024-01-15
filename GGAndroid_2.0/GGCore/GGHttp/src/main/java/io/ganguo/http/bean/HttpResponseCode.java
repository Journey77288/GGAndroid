package io.ganguo.http.bean;

/**
 * 后台返回的状态码
 * <p>
 * Created by hulkyao on 16/6/2016.
 */

public class HttpResponseCode {
    //response code 接口响应成功
    public static final int RESPONSE_CODE_SUCCESS = 200;

    //response body 中code==0时，说明返回数据正常
    public static final int SUCCESS = 0;
    //token过期
    public static final int TOKEN_ERROR = 401;
    //接口发生未知错误
    public static final int SERVER_UNKNOWN_ERROR = 50001;
    //接口无数据返回
    public static final int SERVER_NOT_RESPONDING = 4002;
}
