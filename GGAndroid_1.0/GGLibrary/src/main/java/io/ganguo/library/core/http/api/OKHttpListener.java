package io.ganguo.library.core.http.api;

import io.ganguo.library.core.http.HttpConstants;
import io.ganguo.library.core.http.base.AbstractHttpListener;
import io.ganguo.library.core.http.response.HttpError;
import io.ganguo.library.core.http.response.HttpResponse;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * OK，没有请求内容返回
 * <p/>
 * Created by Tony on 10/17/14.
 */
public abstract class OKHttpListener extends AbstractHttpListener<String> {
    private Logger logger = LoggerFactory.getLogger(HttpConstants.TAG);

    @Override
    public void handleResponse(HttpResponse response) {
        onSuccess(null);
    }

    @Override
    public void handleError(HttpError httpError) {
        onFailure(httpError);
    }

    @Override
    public void onSuccess(String response) {
        try {
            onSuccess();
        } catch (Exception e) {
            logger.e("onSuccess", e);
            logger.e(response);

            HttpError httpError = new HttpError();
            httpError.setCode(HttpConstants.Error.RESPONSE_ERROR.getCode());
            httpError.setMessage(HttpConstants.Error.RESPONSE_ERROR.getMessage());
            onFailure(httpError);
        }
    }


    public abstract void onSuccess();
}
