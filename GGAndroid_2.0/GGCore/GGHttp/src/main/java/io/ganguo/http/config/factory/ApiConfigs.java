package io.ganguo.http.config.factory;


import io.ganguo.http.core.ApiStrategy;
import io.ganguo.utils.util.json.Gsons;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * API默认配置工厂类
 * Created by leo on 2018/7/17.
 */
public final class ApiConfigs {
    public static String TAG = ApiConfigs.class.getSimpleName();

    private ApiConfigs() {
        throw new AssertionError();
    }

    /**
     * Set the base OkHttpClient settings.
     * init OkHttpClient Settings
     *
     * @return
     */
    public static OkHttpClient.Builder createHttpClientBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder;
    }

    /**
     * Set the base Retrofit settings.You can add ConverterFactory or CallAdapterFactory here.
     * retrofit Settings
     *
     * @param apiStrategy
     * @return
     */
    public static Retrofit.Builder createRetrofitBuilder(ApiStrategy apiStrategy) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(apiStrategy.getBaseUrl());
        builder.addConverterFactory(GsonConverterFactory.create(Gsons.getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()));
        return builder;
    }

}
