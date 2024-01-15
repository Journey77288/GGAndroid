package io.ganguo.incubator.bean;

import io.ganguo.incubator.AppEnvironment;

/**
 * Api 接口
 * <p/>
 * Created by Tony on 1/4/15.
 */
public class APIConstants {

    /**
     * 根据环境获取不同的服务器地址
     */
    public final static String BASE_URL = AppEnvironment.BASE_URL;

    /**
     * Login 登录
     */
    public final static String URL_LOGIN = BASE_URL + "/api/login";
    public final static String URL_PERFORMANCES = BASE_URL + "/api/performances";

}
