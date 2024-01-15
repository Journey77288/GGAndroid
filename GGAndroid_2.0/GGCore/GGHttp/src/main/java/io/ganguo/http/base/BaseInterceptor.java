package io.ganguo.http.base;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>
 * Interceptor - 基类
 * </p>
 * Created by leo on 2018/7/18.
 */
public abstract class BaseInterceptor implements Interceptor {
    /**
     * function：new Request.Builder
     *
     * @param chain
     * @return
     */
    public Request.Builder newBuilder(Chain chain) {
        return chain.request().newBuilder();
    }

    /**
     * function：init intercept
     *
     * @param chain
     * @return
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        return initResponse(chain);
    }

    /**
     * function：init Response
     *
     * @param chain
     * @return
     */
    public Response initResponse(Chain chain) throws IOException {
        Request.Builder builder = initBuilder(newBuilder(chain));
        if (builder == null) {
            return chain.proceed(chain.request());
        }
        return chain.proceed(builder.build());
    }

    /**
     * function：init initBuilder
     *
     * @param builder
     * @return
     */
    protected abstract Request.Builder initBuilder(Request.Builder builder);
}
