package io.ganguo.library.core.http.api;

import io.ganguo.library.core.http.HttpConstants;
import io.ganguo.library.core.http.HttpConstants.Error;
import io.ganguo.library.core.http.base.AbstractHttpListener;
import io.ganguo.library.core.http.response.HttpError;
import io.ganguo.library.core.http.response.HttpResponse;
import io.ganguo.library.util.StringUtils;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * 请求返回监听器
 * <p/>
 * Created by zhihui_chen on 14-8-14.
 */
public abstract class HttpResponseListener extends AbstractHttpListener<HttpResponse> {
    private Logger logger = LoggerFactory.getLogger(HttpConstants.TAG);

    @Override
    public void handleResponse(HttpResponse response) {
        if (StringUtils.isEmpty(response.getResponse())) {
            HttpError httpError = new HttpError();
            httpError.setCode(Error.RESPONSE_NULL.getCode());
            httpError.setMessage(Error.RESPONSE_NULL.getMessage());
            onFailure(httpError);
            return;
        }
        try {
            onSuccess(response);
        } catch (Exception e) {
            logger.e("onSuccess", e);
            logger.e(response);

            HttpError httpError = new HttpError();
            httpError.setCode(HttpConstants.Error.RESPONSE_ERROR.getCode());
            httpError.setMessage(HttpConstants.Error.RESPONSE_ERROR.getMessage());
            onFailure(httpError);
        }
    }

}
