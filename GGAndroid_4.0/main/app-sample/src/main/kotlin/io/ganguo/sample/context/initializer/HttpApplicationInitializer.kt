package io.ganguo.sample.context.initializer

import android.app.Application
import com.blankj.utilcode.util.NetworkUtils
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import io.ganguo.appcompat.Toasts
import io.ganguo.core.context.initializer.HttpCoreApplicationInitializer
import io.ganguo.http.retrofit.OkHttpClientFactory
import io.ganguo.http.retrofit.RetrofitService
import io.ganguo.http.retrofit.interceptor.*
import io.ganguo.http.use.HttpUse
import io.ganguo.http.use.creator.ApiServiceCreator
import io.ganguo.http2.bean.HttpHeader
import io.ganguo.log.core.Logger
import io.ganguo.sample.AppEnvironment
import io.ganguo.sample.context.AppContext
import io.ganguo.sample.repository.LocalUser
import io.ganguo.utils.runOnUiThread
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : Http Client Config
 * </pre>
 */
object HttpApplicationInitializer : HttpCoreApplicationInitializer(), ApiServiceCreator {
    private const val CACHE_MAX_SIZE = 50 * 1024 * 1024L// 最多缓存50m接口数据
    private const val STALE_CACHE_DAY = 30// 缓存最近30天数据
    private const val API_CACHE_CONTROL = "Cache-Control"// 接口服务端缓存设置
    private const val API_PRAGMA = "Pragma"// 接口服务端Pragma值，离线缓存需要重写该值

    private val debugInterceptors: Array<Interceptor> by lazy {
        with(AppContext.me()) {
            Stetho.initializeWithDefaults(this)
            arrayOf(
                    //根据需要配置
                    StethoInterceptor(),
                    OkHttpProfilerInterceptor(),
                    ChuckerInterceptor.Builder(this).build()
            )
        }
    }
    private val interceptors by lazy {
        val list = mutableListOf<Interceptor>()
        if (AppEnvironment.isDebug) {
            list.addAll(debugInterceptors)
        }
        list.toTypedArray()
    }


    /**
     * Http Setting
     * @param application Application
     */
    override fun initialize(application: Application) {
        AppEnvironment.initialize(application)
        val builder = HttpUse.HttpUseConfigBuilder(this)
            .setAuthorizationFailHandle {
                // TODO: token失效处理
                LocalUser.get().logout()
            }
        HttpUse.initialize(this, builder)
    }

    /**
     * Retrofit 参数配置
     * @return RetrofitService.Builder
     */
    override fun createRetrofitBuilder(): RetrofitService.ConfigBuilder {
        return RetrofitService
                .ConfigBuilder()
                .apply {
                    applyMainBaseUrl(AppEnvironment.baseUrl)
                    //配置是否支持多域名。后期如需根据后台下发接口，动态添加配置域名，可以写个单例adapter来处理
                    //项目只有一个BaseUrl，则不需要配置该代码
                    //具体使用，请参考HttpDemoVModel代码
                    //applyMultiBaseUrlAdapter(true, MultiUrlAdapter())
                    //配置全局Header
                    applyGlobalHeader(HttpHeader.HeaderKey.CHANNEL, AppEnvironment.flavor)
                    applyGlobalHeader(HttpHeader.HeaderKey.VERSION, AppEnvironment.versionName)
                    applyGlobalHeader(HttpHeader.HeaderKey.FROM, HttpHeader.HeaderValue.ANDROID)
                    applyGlobalHeader(HttpHeader.HeaderKey.USER_AGENT, getUserAgent(AppContext.me()))
                }
    }

    /**
     * OkHttp参数配置
     * @return OkHttpClient.Builder
     */
    override fun createHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClientFactory
                .createOkHttpClientBuilder(*interceptors)
                .addInterceptor(HttpGlobalHeaderInterceptor())
                //----start----//
                //-------以下拦截器根据需求配置，不是必备------//
                .addInterceptor(HttpMultiUrlGlobalParameterInterceptor())
                .addInterceptor(HttpMultiUrlGlobalHeaderInterceptor())
                .addInterceptor(HttpMultiUrlInterceptor())
                .addInterceptor(tokenInterceptor)
                //-----end----//
                .addInterceptor(createLoggingInterceptor())
                .addInterceptor(HttpResponseCodeInterceptor())//接口Response Code 拦截器
                .addInterceptor(object : HttpHeaderInterceptor {
                    override fun getHeaders(chain: Interceptor.Chain): Map<String, Any> {
                        return emptyMap()
                    }
                })
                //-------以下配置为离线缓存配置，不是必备，默认关闭离线缓存------//
//                .addInterceptor(offlineInterceptor)
//                .addNetworkInterceptor(onlineInterceptor)
//                .cache(Cache(AppContext.me().cacheDir, CACHE_MAX_SIZE))
    }

    /**
     * Token 拦截器
     */
    private val tokenInterceptor = Interceptor {
        var request = it.request()
        if (LocalUser.get().isLogin()) {
            val requestBuilder = request.newBuilder()
            val token = LocalUser.get().getToken()
//            Logger.i("token: $token")
            requestBuilder.header(HttpHeader.HeaderKey.TOKEN, token)
            request = requestBuilder.build()
        }

        it.proceed(request)
    }

    /**
     * 离线模式 拦截器
     */
    private val offlineInterceptor = Interceptor {
        var request = it.request()
        if (!NetworkUtils.isAvailable()) {
            request = request.newBuilder()
                .cacheControl(CacheControl.Builder().onlyIfCached().maxStale(STALE_CACHE_DAY, TimeUnit.DAYS).build())
                .removeHeader(API_PRAGMA)
                .build()
            runOnUiThread {
                Toasts.show("Network offline")
            }
        }
        it.proceed(request)
    }

    /**
     * 在线模式 拦截器
     */
    private val onlineInterceptor = Interceptor {
        val response = it.proceed(it.request())
        val maxAge = if (NetworkUtils.isAvailable()) 0 else 60
        response.newBuilder()
            .header(API_CACHE_CONTROL, "public, max-age=$maxAge")
            .removeHeader(API_PRAGMA)
            .build()
    }

    /**
     * HttpLoggingInterceptor
     * @return Interceptor
     */
    private fun createLoggingInterceptor() = let {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Logger.d(message)
            }
        }).apply {
            level = if (AppEnvironment.isDebug) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
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
