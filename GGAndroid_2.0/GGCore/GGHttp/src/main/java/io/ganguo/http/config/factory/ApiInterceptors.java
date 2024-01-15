package io.ganguo.http.config.factory;

import io.ganguo.http.base.BaseInterceptor;
import io.ganguo.http.bean.HttpConstants;
import io.ganguo.http.config.interceptor.HttpClientErrorInterceptor;
import io.ganguo.http.core.ApiStrategy;
import io.ganguo.utils.util.log.Logger;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * <p>
 * API Interceptor 默认配置工厂类
 * </p>
 * Created by leo on 2018/8/15.
 */
public final class ApiInterceptors {
    public static String TAG = ApiInterceptors.class.getSimpleName();

    /**
     * create default Logging Interceptor
     *
     * @param apiStrategy
     * @return
     */
    public static Interceptor createHttpLoggingInterceptor(ApiStrategy apiStrategy) {
        HttpLoggingInterceptor httpLogging = new HttpLoggingInterceptor(message ->
                Logger.tag(TAG).d(message)).setLevel(apiStrategy.isDebug() ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return httpLogging;

    }

    /**
     * create default Header Param Interceptor
     * <p>
     * 注：此处一般配置运行期间不会改变的HEADER_PARAMS 参数
     * <p/>
     *
     * @param apiStrategy
     * @return
     */
    public static Interceptor createDefaultHttpHeaderInterceptor(ApiStrategy apiStrategy) {
        BaseInterceptor headerInterceptor = new BaseInterceptor() {
            @Override
            protected Request.Builder initBuilder(Request.Builder builder) {
                builder
                        .addHeader(HttpConstants.HEADER_PARAMS_FROM, HttpConstants.HEADER_DEFAULT_VALUE_FROM)
                        .addHeader(HttpConstants.HEADER_PARAMS_USER_AGENT, apiStrategy.generateUserAgent())
                        .addHeader(HttpConstants.HEADER_PARAMS_VERSION, apiStrategy.getAppVersionName())
                        .addHeader(HttpConstants.HEADER_PARAMS_CHANNEL, apiStrategy.getAppChannel());
                return builder;
            }
        };
        return headerInterceptor;
    }


    /**
     * create Http Client Response Code Interceptor
     * <p>
     * 配置Response Code拦截Interceptor
     * <p/>
     *
     * @return
     */
    public static Interceptor createHttpClientErrorInterceptor() {
        return new HttpClientErrorInterceptor();
    }
}
