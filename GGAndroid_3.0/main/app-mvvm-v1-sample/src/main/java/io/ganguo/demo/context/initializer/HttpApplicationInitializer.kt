package io.ganguo.demo.context.initializer

import android.app.Application
import io.ganguo.demo.context.AppContext
import io.ganguo.demo.http.domain.DomainConstant
import io.ganguo.demo.http.domain.MultiUrlSettingAdapter
import io.ganguo.http.retrofit.OkHttpClientFactory
import io.ganguo.http.retrofit.RetrofitService
import io.ganguo.http.retrofit.RetrofitSetting
import io.ganguo.http.retrofit.interceptor.HttpAddedBuilderInterceptor
import io.ganguo.http.retrofit.interceptor.HttpMultiUrlInterceptor
import io.ganguo.http.retrofit.interceptor.HttpResponseCodeInterceptor
import io.ganguo.http.use.creator.ApiServiceCreator
import io.ganguo.http.use.HttpUse
import io.ganguo.http2.bean.HttpHeader
import io.ganguo.core.context.initializer.ApplicationInitializer
import io.ganguo.core.context.initializer.ApplicationInitializerCreator
import io.ganguo.log.core.Logger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : Http Client Config
 * </pre>
 */
object HttpApplicationInitializer : ApplicationInitializer, ApplicationInitializerCreator<HttpApplicationInitializer>, RetrofitSetting, ApiServiceCreator {
    /**
     * Http Setting
     * @param application Application
     */
    override fun initialize(application: Application) {
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
                    applyMainBaseUrl(DomainConstant.Domain.DomainURL.DOUBAN)

                    //配置是否支持多域名。后期如需根据后台下发接口，动态添加配置域名，可以写个单例adapter来处理
                    //项目只有一个BaseUrl，则不需要配置该代码
                    applyMultiBaseUrlAdapter(true, MultiUrlSettingAdapter())

                    //配置全局Header
//                    applyGlobalHeader(HttpHeader.HeaderKey.CHANNEL, BuildConfig.FLAVOR)
//                    applyGlobalHeader(HttpHeader.HeaderKey.VERSION, BuildConfig.VERSION_NAME)
//                    applyGlobalHeader(HttpHeader.HeaderKey.FROM, HttpHeader.HeaderValue.ANDROID)
//                    applyGlobalHeader(HttpHeader.HeaderKey.USER_AGENT, getUserAgent(AppContext.get()))

//                    //配置全局参数
//                    applyGlobalParameter("apikey", "0df993c66c0c636e29ecbb5344252a4a")
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
                    override fun addedBuilder(chain: Interceptor.Chain, builder: Request.Builder) {
                        //todo: Add User Token
                        builder.addHeader(HttpHeader.HeaderKey.TOKEN, "")
                    }
                })
    }

    /**
     * print log message
     * @param message String
     */
    override fun log(message: String) {
        Logger.tag(javaClass.simpleName).e(message)
    }


    /**
     * create HttpApplicationInitializer
     * @param parameter Carry configuration parameters
     * @return  Pair<String, T>
     */
    override fun createInitializer(parameter: Map<String, Any>): HttpApplicationInitializer {
        return this
    }

    /**
     * create ApiService Object
     * @param clazz Class<S>
     * @return S
     */
    override fun <S> createApiService(clazz: Class<S>): S {
        return RetrofitService.createApiService(clazz)
    }

}