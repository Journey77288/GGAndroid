package io.ganguo.http.handler;


import io.ganguo.http.entity.response.HttpResponse;
import io.ganguo.http.handler.base.GGResponseHandler;

/**
 * <p>
 * 1、支持甘果java/PHP后台，无分页的API
 * update by leo on 2018/07/15.
 */
public class ApiCommonResponseHandler<T, B extends HttpResponse<T>> extends GGResponseHandler<B, T> {

    /**
     * function：处理接口返回数据类型
     *
     * @param b
     */
    @Override
    protected T onHandlerResponse(B b) {
        return b.getData();
    }

}