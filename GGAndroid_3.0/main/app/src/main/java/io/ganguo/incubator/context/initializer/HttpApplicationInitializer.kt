package io.ganguo.incubator.context.initializer

import android.app.Application
import io.ganguo.app.core.context.initializer.HttpCommonApplicationInitializer
import io.ganguo.http.retrofit.OkHttpClientFactory
import io.ganguo.http.retrofit.RetrofitService
import io.ganguo.http.retrofit.interceptor.HttpAddedBuilderInterceptor
import io.ganguo.http.retrofit.interceptor.HttpMultiUrlInterceptor
import io.ganguo.http.retrofit.interceptor.HttpResponseCodeInterceptor
import io.ganguo.http.use.creator.ApiServiceCreator
import io.ganguo.http.use.HttpUse
import io.ganguo.http2.bean.HttpHeader
import io.ganguo.incubator.AppEnv
import io.ganguo.incubator.context.AppContext
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : Http Client Config
 * </pre>
 */
object HttpApplicationInitializer : HttpCommonApplicationInitializer(), ApiServiceCreator {
    /**
     * Http Setting
     * @param application Application
     */
    override fun initialize(application: Application) {
        AppEnv.initialize(application)
        setting()
        HttpUse.initialize(HttpUse.HttpUseConfigBuilder(this))
    }

    /**
     * Retrofit 参数配置
     * @return RetrofitService.Builder
     */
    override fun createRetrofitBuilder(): RetrofitService.ConfigBuilder {
        return RetrofitService
                .ConfigBuilder()
                .apply {
                    applyMainBaseUrl(AppEnv.baseUrl)
                    //配置是否支持多域名。后期如需根据后台下发接口，动态添加配置域名，可以写个单例adapter来处理
                    //项目只有一个BaseUrl，则不需要配置该代码
                    //具体使用，请参考HttpDemoVModel代码
                    //applyMultiBaseUrlAdapter(true, MultiUrlAdapter())

                    //配置全局Header
                    applyGlobalHeader(HttpHeader.HeaderKey.CHANNEL, AppEnv.flavor)
                    applyGlobalHeader(HttpHeader.HeaderKey.VERSION, AppEnv.versionName)
                    applyGlobalHeader(HttpHeader.HeaderKey.FROM, HttpHeader.HeaderValue.ANDROID)
                    applyGlobalHeader(HttpHeader.HeaderKey.USER_AGENT, getUserAgent(AppContext.get()))
                }
    }

    /**
     * OkHttp参数配置
     * @return OkHttpClient.Builder
     */
    override fun createHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClientFactory
                .createDefaultBuilder(true, AppContext.get(), this)
                //接口 Response Code 拦截器
                .addInterceptor(HttpResponseCodeInterceptor())
                //多域名||动态域名 配置拦截器
                .addInterceptor(HttpMultiUrlInterceptor())
                .addInterceptor(object : HttpAddedBuilderInterceptor {
                    override fun addedBuilder(builder: Request.Builder) {
                        //todo: Add User Token
                        builder.addHeader(HttpHeader.HeaderKey.TOKEN, "")
                    }
                })
    }

    /**
     * 创建Api服务对象
     * @param clazz Class<S>
     * @return S
     */
    override fun <S> createApiService(clazz: Class<S>): S {
        return RetrofitService.createApiService(clazz)
    }


}