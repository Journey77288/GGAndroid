package io.ganguo.http.core;

import android.support.annotation.NonNull;

import java.util.Arrays;

import io.ganguo.http.config.factory.ApiConfigs;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Base Api Config
 * update by leo on 1/08/2018.
 */
public class ApiConfig {
    protected Builder builder;

    private ApiConfig(Builder builder) {
        this.builder = builder;
    }

    /**
     * Get base OkHttpClient, which build with base Http Settings
     *
     * @return
     */
    public OkHttpClient getHttpClient() {
        return builder.getHttpClient();
    }


    /**
     * function： get Retrofit.Builder
     *
     * @return
     */
    public Retrofit.Builder getRetrofitBuilder() {
        return builder.getRetrofitBuilder();
    }

    /**
     * function：get OkHttpClient.Builder
     *
     * @return
     */
    public OkHttpClient.Builder getClientBuilder() {
        return builder.getClientBuilder();
    }


    /**
     * function：get ApiStrategy
     *
     * @return
     */
    public ApiStrategy getApiStrategy() {
        return builder.apiStrategy;
    }


    /**
     * function：添加 Interceptor
     *
     * @param interceptor
     * @return
     */
    public ApiConfig addInterceptor(Interceptor interceptor) {
        if (interceptor != null) {
            getClientBuilder()
                    .interceptors()
                    .add(interceptor);
        }
        return this;
    }

    /**
     * function：添加多个 Interceptor
     *
     * @param interceptor
     * @return
     */
    public ApiConfig addInterceptors(Interceptor... interceptor) {
        getClientBuilder()
                .interceptors()
                .addAll(Arrays.asList(interceptor));
        return this;
    }

    /**
     * 使用建造者模式来创建Http配置，避免配置遗漏或者后期配置不生效的问题
     */
    public static class Builder {
        //clientBuilder
        private OkHttpClient.Builder clientBuilder;
        //httpClient
        private OkHttpClient httpClient;
        //retrofitBuilder
        private Retrofit.Builder retrofitBuilder;
        //retrofitBuilder
        private ApiStrategy apiStrategy;

        public Builder(ApiStrategy apiStrategy) {
            this.apiStrategy = apiStrategy;
        }

        /**
         * create api config
         *
         * @return
         */
        public ApiConfig build() {
            return new ApiConfig(this);
        }


        /**
         * Get base OkHttpClient, which build with base Http Settings
         *
         * @param appendOkHttpFunc
         * @param appendRetrofitFunc
         * @return
         */
        public Builder apply(@NonNull Function<OkHttpClient.Builder, OkHttpClient.Builder> appendOkHttpFunc, @NonNull Function<Retrofit.Builder, Retrofit.Builder> appendRetrofitFunc) {
            applyOKHttp(appendOkHttpFunc);
            applyRetrofit(appendRetrofitFunc);
            return this;
        }


        /**
         * function：追加全局 OkHttpClient 设置
         *
         * @param appendFunc
         * @return
         */
        public Builder applyOKHttp(@NonNull Function<OkHttpClient.Builder, OkHttpClient.Builder> appendFunc) {
            try {
                OkHttpClient.Builder builder = getClientBuilder();
                this.clientBuilder = appendFunc.apply(builder);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }


        /**
         * function：追加全局 Retrofit 设置
         *
         * @param appendFunc
         * @return
         */
        public Builder applyRetrofit(@NonNull Function<Retrofit.Builder, Retrofit.Builder> appendFunc) {
            try {
                Retrofit.Builder rBuilder = getRetrofitBuilder();
                this.retrofitBuilder = appendFunc.apply(rBuilder);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }


        /**
         * function：添加 Interceptor
         * <p>
         * 注：添加自定义Interceptor拦截器，token配置等
         * <p/>
         *
         * @param interceptor
         * @return
         */
        public Builder addInterceptor(Interceptor interceptor) {
            if (interceptor != null) {
                getClientBuilder()
                        .interceptors()
                        .add(interceptor);
            }
            return this;
        }

        /**
         * function：添加多个 Interceptor
         * <p>
         * 注：添加自定义Interceptor拦截器，token配置等
         * <p/>
         *
         * @param interceptor
         * @return
         */
        public Builder addInterceptors(Interceptor... interceptor) {
            getClientBuilder()
                    .interceptors()
                    .addAll(Arrays.asList(interceptor));
            return this;
        }

        /**
         * function： get Retrofit.Builder
         *
         * @return
         */
        public Retrofit.Builder getRetrofitBuilder() {
            if (retrofitBuilder == null) {
                retrofitBuilder = ApiConfigs.createRetrofitBuilder(apiStrategy);
            }
            return retrofitBuilder;
        }

        /**
         * function：get OkHttpClient.Builder
         *
         * @return
         */
        public OkHttpClient.Builder getClientBuilder() {
            if (clientBuilder == null) {
                clientBuilder = ApiConfigs.createHttpClientBuilder();
            }
            return clientBuilder;
        }

        /**
         * function：get OkHttpClient
         *
         * @return
         */
        public OkHttpClient getHttpClient() {
            if (httpClient == null) {
                httpClient = getClientBuilder().build();
            }
            return httpClient;
        }
    }
}
