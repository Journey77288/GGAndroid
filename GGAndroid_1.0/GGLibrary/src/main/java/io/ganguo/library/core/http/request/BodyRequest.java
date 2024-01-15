package io.ganguo.library.core.http.request;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 主体请求类
 * <p/>
 * Created by Tony on 10/18/14.
 */
public class BodyRequest extends Request {
    private StringEntity httpEntity;

    public BodyRequest(String url, HttpMethod method) {
        super(url, method);
    }

    public void setContentType(String contentType) {
        httpEntity.setContentType(contentType + "; charset=" + getEncoding());
    }

    public void setBody(String bodyStr) {
        try {
            httpEntity = new StringEntity(bodyStr, getEncoding());
            httpEntity.setContentType("application/json; charset=" + getEncoding());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException: ", e);
        }
    }

    public void setBody(JSONObject bodyJson) {
        setBody(bodyJson.toString());
    }

    public void setBody(JSONArray bodyJson) {
        setBody(bodyJson.toString());
    }

    @Override
    public HttpEntity getHttpEntity() {
        return httpEntity;
    }
}
