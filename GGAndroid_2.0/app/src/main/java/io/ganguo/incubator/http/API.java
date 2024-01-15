package io.ganguo.incubator.http;


import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import io.ganguo.http.config.factory.ApiInterceptors;
import io.ganguo.http.config.header.HeaderUserInfoInterceptor;
import io.ganguo.http.core.ApiConfig;
import io.ganguo.http.core.ApiManager;
import io.ganguo.http.core.ApiStrategy;
import io.ganguo.incubator.AppContext;
import io.ganguo.incubator.BuildConfig;
import io.ganguo.utils.common.LoadingHelper;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.util.log.Logger;
import okhttp3.Request;

/**
 * <p>
 * Http配置工具类
 * </p>
 * Created by leo on 2018/7/26.
 */
public class API {
    public static final String HEADER_TOKEN_KEY = "Authorization";//token header key
    public AppContext context;
    private static API INSTANCE;

    static {
        INSTANCE = new API();
    }

    /**
     * function：单例
     * <p>
     * 饥汉模式
     * <p/>
     *
     * @return
     */
    protected static API get() {
        return INSTANCE;
    }


    /**
     * function：set AppContext
     *
     * @return
     */
    public API setContext(AppContext context) {
        this.context = context;
        return this;
    }

    /**
     * /**
     * function：get AppContext
     *
     * @return
     */
    public AppContext getContext() {
        return context;
    }

    /**
     * function：init config
     */
    public static void init(AppContext context) {
        get().setContext(context);
        get().initHttp();
    }

    /**
     * function：init http config
     */
    private void initHttp() {
        ApiStrategy apiStrategy = onApiStrategyConfig();
        ApiConfig config = onHttpApiConfig(apiStrategy);
        ApiManager.init(config);
    }

    /**
     * function：API请求基本参数配置
     *
     * @return
     */
    protected ApiStrategy onApiStrategyConfig() {
        ApiStrategy apiStrategy = new ApiStrategy
                .Builder(getContext())
                //BaseUrl可以在此处配置，如果项目涉及到多BaseUrl，可以在创建http module时，重新设置
                .setBaseUrl(BuildConfig.BASE_URL)
                .setAppVersionName(BuildConfig.VERSION_NAME)
                .setDebug(BuildConfig.DEBUG)
                .setAppChannel(BuildConfig.FLAVOR)
                .setUnAuthorizedCallBack(e -> {
                    // TODO: 2018/8/14 Token失效/未认证监听回调，可以在这里做一些Token过期处理
                    ToastHelper.showMessage( "token error：" + e.getMessage());
                })
                .setApiExceptionCallBack(e -> {
                    Logger.e("token error:code:" + e.getCode() + "message:" + e.getMessage());
                    ToastHelper.showMessage( "error：" + e.getMessage());
                })
                .setExceptionCallBack(e -> LoadingHelper.hideMaterLoading())
                .setNetworkErrorCallBack(e -> ToastHelper.showMessage( " error：" + e.getMessage()))
                .build();
        return apiStrategy;
    }

    /**
     * function：API请求参数配置
     *
     * @param apiStrategy
     * @return
     */
    protected ApiConfig onHttpApiConfig(ApiStrategy apiStrategy) {
        ApiConfig apiConfig = new ApiConfig
                .Builder(apiStrategy)
                .addInterceptor(new ChuckInterceptor(apiStrategy.getContext()))
                //配置Response Code拦截Interceptor
                .addInterceptor(ApiInterceptors.createHttpClientErrorInterceptor())
                //配置接口数据打印Interceptor
                .addInterceptor(ApiInterceptors.createHttpLoggingInterceptor(apiStrategy))
                //配置默认Header（运行期间不会动态改变的Header参数）
                .addInterceptor(ApiInterceptors.createDefaultHttpHeaderInterceptor(apiStrategy))
                // 添加用户Token
                .addInterceptor(createTokenInterceptor())
                // 配置请求超时时长
                .applyOKHttp(builder -> builder.connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS))
                .build();
        return apiConfig;
    }


    /**
     * function：userInfo error interceptor
     *
     * @return
     */
    protected HeaderUserInfoInterceptor createTokenInterceptor() {
        return new HeaderUserInfoInterceptor() {
            @Override
            protected Request.Builder initBuilder(Request.Builder builder) {
                // TODO: 2018/8/14 在这里添加用户Token
                builder.addHeader(HEADER_TOKEN_KEY, "");
                return builder;
            }
        };
    }

}
