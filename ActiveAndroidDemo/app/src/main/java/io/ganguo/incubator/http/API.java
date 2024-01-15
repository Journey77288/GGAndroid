package io.ganguo.incubator.http;

import android.os.Build;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.ganguo.incubator.AppContext;
import io.ganguo.incubator.AppEnv;
import io.ganguo.library.util.Systems;
import io.ganguo.library.util.gson.Gsons;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * api service 产生器
 * <p/>
 * Created by Tony on 10/22/15.
 */
public class API {

    /**
     * api base
     */
    public static final String API_BASE_URL = AppEnv.BASE_URL;

    /**
     * okhttp
     */
    private static OkHttpClient httpClient = new OkHttpClient();
    /**
     * service singleton
     */
    private static Map<Class, Object> mServices = new ConcurrentHashMap<>();

    private static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // app/1.0_dev (android; 4.4.4; 19)
            String userAgent = "app/" + Systems.getVersionName(AppContext.me()) + " (android; " + Build.VERSION.RELEASE + "; " + Build.VERSION.SDK_INT + ")";
            // 1.0.0
            String version = Systems.getVersionName(AppContext.me()) + "";
            String token = "";
            // TODO: 7/12/15 在这里添加header - token
            Request request = chain
                    .request()
                    .newBuilder()
                    .addHeader("User-Agent", userAgent)
                    .addHeader("version", version)
                    .addHeader("token", token)
                    .addHeader("from", "android")
                    .build();
            return chain.proceed(request);
        }
    };

    /**
     * retrofit builder
     */
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(Gsons.getGson()));

    /**
     * 创建一个api service(单例)
     *
     * @param clazz
     * @param <S>
     * @return
     */
    public static <S> S of(Class<S> clazz) {
        if (mServices.containsKey(clazz)) {
            return (S) mServices.get(clazz);
        }
        //添加拦截器
        httpClient.interceptors().add(interceptor);

        Retrofit retrofit = builder.client(httpClient).build();
        S service = retrofit.create(clazz);
        mServices.put(clazz, service);
        return service;
    }

}
