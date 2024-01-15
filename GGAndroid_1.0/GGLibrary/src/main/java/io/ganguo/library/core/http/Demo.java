package io.ganguo.library.core.http;

import io.ganguo.library.core.http.api.HttpResponseListener;
import io.ganguo.library.core.http.request.BodyRequest;
import io.ganguo.library.core.http.request.HttpMethod;
import io.ganguo.library.core.http.request.HttpRequest;
import io.ganguo.library.core.http.response.HttpError;
import io.ganguo.library.core.http.response.HttpResponse;

/**
 * Created by Tony on 10/16/14.
 */
public class Demo {
    public void test() {

        // api/goods/1          REST
        // api/goods?id=1       NOR

        String url = "http://www.baidu.com/goods";

        HttpRequest getRequest = new HttpRequest(url, HttpMethod.GET);

        // /goods?pwd=tony
        HttpRequest postRequest = new HttpRequest(url, HttpMethod.POST);
        postRequest.addTextBody("pwd", "tony");

        BodyRequest putRequest = new BodyRequest(url, HttpMethod.PUT);
        putRequest.setBody("json string");

        // /goods?id=123
        HttpRequest delRequest = new HttpRequest(url, HttpMethod.DELETE);
        postRequest.addTextBody("id", "123");

        HttpFactory.getHttpService().sendRequest(postRequest, new HttpResponseListener() {
            @Override
            public void onSuccess(HttpResponse response) {
//                User user = response.convert(User.class);
            }

            // 可以不实现 默认执行 toast message
            @Override
            public void onFailure(HttpError error) {
                super.onFailure(error);
            }

        });
    }
}
