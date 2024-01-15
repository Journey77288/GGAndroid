package io.ganguo.library.core.http.request;


import org.apache.http.entity.ContentType;

import io.ganguo.library.core.http.util.URLBuilder;


/**
 * HTTP请求
 * url, method
 * headerMap,  postParams
 * <p/>
 * Created by Tony on 10/16/14.
 */
public class HttpRequest extends Request {

    public HttpRequest(String url, HttpMethod method) {
        super(url, method);
    }

    public HttpRequest(URLBuilder builder, HttpMethod method) {
        super(builder.build(), method);
    }

    /**
     * name=value
     * if array
     * name[]=value
     * name[]=value
     *
     * @param key
     * @param value
     */
    public void addTextBody(String key, String value) {
        if (value == null) value = "";

        getEntityBuilder().addTextBody(key, value, ContentType.create("application/x-www-form-urlencoded", getEncoding()));
    }

    /**
     * name[]=value1
     * name[]=value2
     *
     * @param key
     * @param values
     */
    public void addTextBody(String key, String... values) {
        for (String value : values) {
            addTextBody(key, value);
        }
    }

}
