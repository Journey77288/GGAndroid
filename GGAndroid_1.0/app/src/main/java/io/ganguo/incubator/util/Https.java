package io.ganguo.incubator.util;

import android.util.Log;

import org.json.JSONObject;

import io.ganguo.library.core.http.interceptor.HttpInterceptor;
import io.ganguo.library.core.http.interceptor.HttpInterceptorManager;
import io.ganguo.library.core.http.request.HttpMethod;
import io.ganguo.library.core.http.request.HttpRequest;
import io.ganguo.library.core.http.response.HttpError;
import io.ganguo.library.core.http.response.HttpResponse;
import io.ganguo.library.core.http.util.URLBuilder;
import io.ganguo.library.util.StringUtils;
import io.ganguo.library.util.log.GLog;

/**
 * Http 扩展
 * <p/>
 * Created by Tony on 3/6/15.
 */
public class Https {

    /**
     * 添加一些默认请求内容
     *
     * @param url
     * @return
     */
    public static URLBuilder urlBuilder(String url) {
        URLBuilder builder = new URLBuilder(url);
//        builder.append("token", "test_token");
        return builder;
    }

    /**
     * 添加一些默认请求内容
     *
     * @param builder
     * @param method
     * @return
     */
    public static HttpRequest httpRequest(URLBuilder builder, HttpMethod method) {
        return httpRequest(builder.build(), method);
    }

    /**
     * 添加一些默认请求内容
     *
     * @param url
     * @param method
     * @return
     */
    public static HttpRequest httpRequest(String url, HttpMethod method) {
        HttpRequest httpRequest = new HttpRequest(url, method);
//        httpRequest.addTextBody("auth_token", "test_token");
        return httpRequest;
    }

    /**
     * 初始化 错误解析
     */
    public static void init() {
        HttpInterceptorManager.getInstance().addInterceptor(new HttpInterceptor() {
            @Override
            public HttpResponse response(HttpResponse response) throws HttpError {
                if (StringUtils.isNotEmpty(response.getResponse())) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.getResponse());
                        HttpError serverError = null;
                        // 返回的code
                        if (jsonObject.has("code")) {
                            if (serverError == null) {
                                serverError = new HttpError();
                            }
                            serverError.setCode(jsonObject.getInt("code"));
                        }
                        // 返回的message
                        if (jsonObject.has("message")) {
                            if (serverError == null) {
                                serverError = new HttpError();
                            }
                            serverError.setMessage(jsonObject.getString("message"));
                        }
                        if (serverError != null) {
                            serverError.setResponse(response.getResponse());
                            GLog.e("DefaultErrorDecode", serverError);
                            throw serverError;
                        }
                    } catch (HttpError httpError) {
                        throw httpError;
                    } catch (Exception e) {
                        Log.e("DefaultErrorDecode", "handleError", e);
                    }
                }
                return response;
            }

            @Override
            public HttpError error(HttpError httpError) {
                if (StringUtils.isNotEmpty(httpError.getResponse())) {
                    try {
                        JSONObject jsonObject = new JSONObject(httpError.getResponse());
                        HttpError serverError = null;
                        // 返回的code
                        if (jsonObject.has("code")) {
                            if (serverError == null) {
                                serverError = new HttpError();
                            }
                            serverError.setCode(jsonObject.getInt("code"));
                        }
                        // 返回的message
                        if (jsonObject.has("message")) {
                            if (serverError == null) {
                                serverError = new HttpError();
                            }
                            serverError.setMessage(jsonObject.getString("message"));
                        }
                        if (serverError != null) {
                            return serverError;
                        }
                    } catch (Exception e) {
                        Log.e("DefaultErrorDecode", "handleError", e);
                    }
                }
                return httpError;
            }
        });
    }

}
