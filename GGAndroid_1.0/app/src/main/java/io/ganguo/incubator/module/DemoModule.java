package io.ganguo.incubator.module;

import io.ganguo.incubator.bean.APIConstants;
import io.ganguo.library.core.http.HttpFactory;
import io.ganguo.library.core.http.api.HttpResponseListener;
import io.ganguo.library.core.http.request.HttpMethod;
import io.ganguo.library.core.http.request.HttpRequest;
import io.ganguo.library.core.http.response.HttpResponse;
import io.ganguo.library.core.http.util.URLBuilder;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * Created by Tony on 3/10/15.
 */
public class DemoModule {
    private static final Logger LOG = LoggerFactory.getLogger(DemoModule.class);

    public static void demo() {
        URLBuilder builder = new URLBuilder(APIConstants.URL_PERFORMANCES);
        // /api/goods?id=123
        builder.append("id", "123");
        // /api/goods/123 if /123?id=123
        builder.append("123");
        // /api/goods/:id to /api/goods/123
        builder.replace(":id", "123");

        HttpRequest httpRequest = new HttpRequest(builder, HttpMethod.GET);
        HttpFactory.getHttpService().sendRequest(httpRequest, new HttpResponseListener() {
            @Override
            public void onSuccess(HttpResponse response) {

            }
        });
    }

    public static void getPerformances() {
        URLBuilder builder = new URLBuilder(APIConstants.URL_PERFORMANCES);
        LOG.d(builder);

        HttpRequest httpRequest = new HttpRequest(builder, HttpMethod.GET);
        HttpFactory.getHttpService().sendRequest(httpRequest, new HttpResponseListener() {
            @Override
            public void onSuccess(HttpResponse response) {

            }
        });
    }

}
