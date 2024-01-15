package io.ganguo.http.core;

import android.support.annotation.NonNull;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.List;

import io.ganguo.http.config.header.HeaderUserInfoInterceptor;
import io.ganguo.utils.util.Strings;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Api 管理器
 * Created by Roger on 30/12/2016.
 * update by Leo on 30/07/2018.
 */
public final class ApiManager {
    private static ApiConfig apiConfig;
    private static ApiStrategy apiStrategy;


    /**
     * function：可初始化追加全局配置后的 ApiConfig
     *
     * @param config
     */
    public static void init(@NonNull ApiConfig config) {
        apiConfig = config;
        apiStrategy = config.getApiStrategy();
        checkApiConfig();
    }

    /**
     * function：检查API请求必备配置
     */
    protected static void checkApiConfig() {
        if (apiStrategy == null) {
            throw new IllegalArgumentException("Illegal apiStrategy == null");
        }
        if (Strings.isEmpty(apiStrategy.getBaseUrl())) {
            throw new IllegalArgumentException("Illegal baseURL: url == null");
        }
        checkTokenStrategy();
    }

    /**
     * function：检查是否有添加 HeaderTokenInterceptor
     */
    protected static void checkTokenStrategy() {
        List<Interceptor> interceptors = apiConfig.getHttpClient().interceptors();
        for (Interceptor interceptor : interceptors) {
            if (interceptor instanceof HeaderUserInfoInterceptor) {
                return;
            }
        }
        throw new IllegalArgumentException("Illegal Please add  HeaderUserInfoInterceptor!!!");
    }

    /**
     * function：获取BaseUrl
     *
     * @param newBaseUrl 如果newBaseUrl不为null，优先拿newBaseUrl
     * @return
     */
    private static String onInterceptorBaseUrl(String newBaseUrl) {
        if (Strings.isNotEmpty(newBaseUrl)) {
            return newBaseUrl;
        }
        return getApiStrategy().getBaseUrl();
    }


    /**
     * function：get ApiConfig
     *
     * @return
     */
    public static <S extends ApiConfig> S getGlobalConfig() {
        return (S) apiConfig;
    }

    /**
     * function：get ApiStrategy
     *
     * @return
     */
    public static ApiStrategy getApiStrategy() {
        return apiStrategy;
    }

    /**
     * function：Retrofit.Builder
     *
     * @return
     */
    public static Retrofit.Builder getRetrofitBuilder() {
        return getGlobalConfig().getRetrofitBuilder();
    }

    /**
     * function：get OkHttpClient
     *
     * @return
     */
    public static OkHttpClient getHttpClient() {
        return getGlobalConfig().getHttpClient();
    }

    /**
     * function：create retrofit
     *
     * @param clazz http module class类型
     * @return
     */
    public static <S> S create(Class<S> clazz) {
        return create(clazz, null);
    }

    /**
     * function：create retrofit
     *
     * @param baseUrl baseUrl 需要动态设置则传，不传则默认为ApiStrategy.getBaseUrl()参数
     * @param clazz   http module class类型
     * @return
     */
    public static <S> S create(Class<S> clazz, String baseUrl) {
        return create(getHttpClient(), getRetrofitBuilder(), clazz, baseUrl);
    }

    /**
     * function：create retrofit
     *
     * @param baseUrl baseUrl 需要动态设置则传，不传则默认为ApiStrategy.getBaseUrl()参数
     * @param clazz   http module class类型
     * @param builder Retrofit配置
     * @return
     */
    public static <S> S create(Retrofit.Builder builder, Class<S> clazz, String baseUrl) {
        return create(getHttpClient(), builder, clazz, baseUrl);
    }

    /**
     * function：create retrofit
     *
     * @param baseUrl baseUrl 需要动态设置则传，不传则默认为ApiStrategy.getBaseUrl()参数
     * @param client  HttpClient配置
     * @param clazz   http module class类型
     * @return
     */
    public static <S> S create(OkHttpClient client, Class<S> clazz, String baseUrl) {
        return create(client, getRetrofitBuilder(), clazz, baseUrl);
    }


    /**
     * function：create retrofit
     *
     * @param newBaseUrl baseUrl 需要动态设置则传，不传则默认为ApiStrategy.getBaseUrl()参数
     * @param client     HttpClient配置
     * @param clazz      http module class类型
     * @param builder    Retrofit配置
     * @param
     */
    public static <S> S create(OkHttpClient client, Retrofit.Builder builder, Class<S> clazz, String newBaseUrl) {
        String baseUrl = onInterceptorBaseUrl(newBaseUrl);
        builder.baseUrl(baseUrl);
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(clazz);
    }
}
