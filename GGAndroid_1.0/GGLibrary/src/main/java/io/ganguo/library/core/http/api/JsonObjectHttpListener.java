package io.ganguo.library.core.http.api;

import org.json.JSONException;
import org.json.JSONObject;

import io.ganguo.library.core.http.HttpConstants;
import io.ganguo.library.core.http.base.AbstractHttpListener;
import io.ganguo.library.core.http.response.HttpError;
import io.ganguo.library.core.http.response.HttpResponse;
import io.ganguo.library.util.StringUtils;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;


/**
 * JsonObject请求返回监听器
 * <p/>
 * Created by zhihui_chen on 14-8-14.
 */
public abstract class JsonObjectHttpListener extends AbstractHttpListener<JSONObject> {
    private Logger logger = LoggerFactory.getLogger(HttpConstants.TAG);

    @Override
    public void handleResponse(HttpResponse response) {
        if (StringUtils.isEmpty(response.getResponse())) {
            HttpError httpError = new HttpError();
            httpError.setCode(HttpConstants.Error.RESPONSE_NULL.getCode());
            httpError.setMessage(HttpConstants.Error.RESPONSE_NULL.getMessage());
            onFailure(httpError);
            return;
        }
        try {
            onSuccess(new JSONObject(response.getResponse()));
        } catch (JSONException e) {
            logger.e("TO JSONObject", e);
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
