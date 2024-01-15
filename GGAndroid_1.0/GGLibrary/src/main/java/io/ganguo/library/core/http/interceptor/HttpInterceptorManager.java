package io.ganguo.library.core.http.interceptor;

import java.util.LinkedList;
import java.util.List;

import io.ganguo.library.core.http.response.HttpResponse;
import io.ganguo.library.core.http.response.HttpError;

/**
 * Created by Tony on 3/4/15.
 */
public class HttpInterceptorManager {

    private static HttpInterceptorManager singleton = new HttpInterceptorManager();
    private List<HttpInterceptor> httpInterceptors = new LinkedList<HttpInterceptor>();

    private HttpInterceptorManager() {
//        httpInterceptors.add(new DefaultErrorHttpInterceptor());
    }

    public static HttpInterceptorManager getInstance() {
        return singleton;
    }

    public void addInterceptor(HttpInterceptor httpInterceptor) {
        httpInterceptors.add(httpInterceptor);
    }

    public List<HttpInterceptor> getHttpInterceptors() {
        return httpInterceptors;
    }

    public HttpResponse invoke(HttpResponse response) throws HttpError {
        for (HttpInterceptor httpInterceptor : httpInterceptors) {
            response = httpInterceptor.response(response);
        }
        return response;
    }

    public HttpError invoke(HttpError httpError) {
        for (HttpInterceptor httpInterceptor : httpInterceptors) {
            httpError = httpInterceptor.error(httpError);
        }
        return httpError;
    }
}
