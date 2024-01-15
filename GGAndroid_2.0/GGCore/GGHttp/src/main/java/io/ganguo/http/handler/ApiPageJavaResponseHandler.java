package io.ganguo.http.handler;


import io.ganguo.http.entity.dto.PageJavaDTO;
import io.ganguo.http.handler.base.GGResponseHandler;

/**
 * <p>
 * 1、支持甘果java后台，有分页API
 * 注：如果是客户/第三方API数据规范，请按第三方数据规范，自定义HttpHandler
 * </p>
 * Created by leo on 2018/7/15.
 */
public class ApiPageJavaResponseHandler<T> extends GGResponseHandler<PageJavaDTO<T>, PageJavaDTO<T>> {
    @Override
    protected PageJavaDTO<T> onHandlerResponse(PageJavaDTO<T> dto) {
        return dto;
    }
}
