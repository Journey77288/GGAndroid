package io.ganguo.library.core.http.interceptor;

import io.ganguo.library.core.http.response.HttpResponse;
import io.ganguo.library.core.http.response.HttpError;

/**
 * Created by Tony on 3/4/15.
 */
public abstract class AbstractHttpInterceptor implements HttpInterceptor {

    @Override
    public HttpResponse response(HttpResponse response) {
        return response;
    }

    @Override
    public HttpError error(HttpError httpError) {
        return httpError;
    }
}
