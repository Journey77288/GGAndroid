package io.ganguo.library.core.http.api;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import io.ganguo.library.core.http.HttpConstants;
import io.ganguo.library.core.http.base.AbstractHttpListener;
import io.ganguo.library.core.http.response.HttpError;
import io.ganguo.library.core.http.response.HttpResponse;
import io.ganguo.library.util.StringUtils;
import io.ganguo.library.util.gson.GsonUtils;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * 带泛型的 HttpListener
 * <p/>
 * Created by tony on 9/15/14.
 */
public abstract class DataHttpListener<T> extends AbstractHttpListener<T> {
    private Logger logger = LoggerFactory.getLogger(HttpConstants.TAG);

    @Override
    public void handleResponse(HttpResponse response) {
        try {
            if (StringUtils.isEmpty(response.getResponse())) {
                HttpError httpError = new HttpError();
                httpError.setCode(HttpConstants.Error.RESPONSE_NULL.getCode());
                httpError.setMessage(HttpConstants.Error.RESPONSE_NULL.getMessage());
                onFailure(httpError);
                return;
            }

            T data = GsonUtils.fromJson(response.getResponse(), new TypeToken<T>() {
            }.getType());

            onSuccess(data);
        } catch (JsonSyntaxException e) {
            logger.e("GsonError", e);
            logger.e(response);

            HttpError httpError = new HttpError();
            httpError.setCode(HttpConstants.Error.PARSE_ERROR.getCode());
            httpError.setMessage(HttpConstants.Error.PARSE_ERROR.getMessage());
            onFailure(httpError);
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
