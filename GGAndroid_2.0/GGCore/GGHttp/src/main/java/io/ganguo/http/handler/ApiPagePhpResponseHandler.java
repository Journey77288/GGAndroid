package io.ganguo.http.handler;


import io.ganguo.http.entity.dto.PagePhpDTO;
import io.ganguo.http.entity.response.HttpResponse;

/**
 * <p>
 * 1、支持甘果PHP后台，有分页的API
 * 注：如果是客户/第三方API数据规范，请按第三方数据规范，自定义HttpHandler
 * <p/>
 * update by leo on 2018/07/15.
 */
public class ApiPagePhpResponseHandler<T> extends ApiCommonResponseHandler<PagePhpDTO<T>, HttpResponse<PagePhpDTO<T>>> {
}
