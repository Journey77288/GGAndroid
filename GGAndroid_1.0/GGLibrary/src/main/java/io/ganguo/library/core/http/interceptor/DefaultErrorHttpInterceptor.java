package io.ganguo.library.core.http.interceptor;

import android.util.Log;

import org.json.JSONObject;

import io.ganguo.library.core.http.HttpConstants;
import io.ganguo.library.core.http.response.HttpError;
import io.ganguo.library.util.StringUtils;

/**
 * Created by Tony on 3/4/15.
 */
public class DefaultErrorHttpInterceptor extends AbstractHttpInterceptor {

    @Override
    public HttpError error(HttpError httpError) {
        if (StringUtils.isNotEmpty(httpError.getResponse())) {
            HttpError serverError = new HttpError();
            try {
                JSONObject jsonObject = new JSONObject(httpError.getResponse());
                if (jsonObject.has("error")) {
                    serverError.setCode(jsonObject.getInt("error"));
                }
                if (jsonObject.has("code")) {
                    serverError.setCode(jsonObject.getInt("code"));
                }
                if (jsonObject.has("msg")) {
                    serverError.setMessage(jsonObject.getString("msg"));
                }
                if (jsonObject.has("message")) {
                    serverError.setMessage(jsonObject.getString("message"));
                }
                return serverError;
            } catch (Exception e) {
                Log.e("DefaultErrorDecode", "handleError", e);

                serverError.setCode(HttpConstants.Error.PARSE_ERROR.getCode());
                serverError.setMessage(HttpConstants.Error.PARSE_ERROR.getMessage());
                return serverError;
            }
        }
        return super.error(httpError);
    }
}
