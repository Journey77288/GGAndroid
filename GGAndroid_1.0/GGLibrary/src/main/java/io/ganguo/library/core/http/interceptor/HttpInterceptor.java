package io.ganguo.library.core.http.interceptor;

import io.ganguo.library.core.http.response.HttpResponse;
import io.ganguo.library.core.http.response.HttpError;

/**
 * Created by Tony on 3/4/15.
 */
public interface HttpInterceptor {

    public HttpResponse response(HttpResponse response) throws HttpError;

    public HttpError error(HttpError httpError);

}
