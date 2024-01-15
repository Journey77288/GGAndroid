package io.ganguo.library.core.http.base;

import io.ganguo.library.core.http.request.Request;

/**
 * Created by zhihui_chen on 14-8-4.
 */
public interface HttpService {

    /**
     * 发送一个请求
     *
     * @param request
     * @param httpListener
     */
    public void sendRequest(Request request, HttpListener<?> httpListener);

    /**
     * 添加请求头信息
     *
     * @param key
     * @param value
     */
    public void addHeader(String key, String value);
}
