package io.ganguo.library.core.http.base;

import io.ganguo.library.core.http.response.HttpResponse;
import io.ganguo.library.core.http.response.HttpError;

/**
 * Created by zhihui_chen on 14-8-13.
 */
public interface HttpListener<T> {
    public void handleResponse(HttpResponse response);

    public void handleError(HttpError httpError);

    public void onStart();

    public void onSuccess(T response);

    public void onFailure(HttpError error);

    public void onFinish();
}
