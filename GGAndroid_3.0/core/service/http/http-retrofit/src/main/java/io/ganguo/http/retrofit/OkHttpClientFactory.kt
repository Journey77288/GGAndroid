package io.ganguo.http.retrofit

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/29
 *     desc   : OkHttp Client 工厂类
 * </pre>
 */
object OkHttpClientFactory {

    /**
     * create  OkHttpClient.Builder
     * @return OkHttpClient.Builder
     */
    @JvmStatic
    fun createBuilder(): OkHttpClient.Builder {
        return OkHttpClient
                .Builder()
                .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
    }


    /**
     * create default Builder
     * @param debugInterceptors MutableList<Interceptor>
     * @return OkHttpClient.Builder
     */
    @JvmStatic
    fun createDefaultBuilder(debugInterceptors: MutableList<Interceptor>): OkHttpClient.Builder {
        return createBuilder().apply {
            debugInterceptors.forEach {
                addInterceptor(it)
            }
        }
    }

    /**
     * create default Builder
     * @return OkHttpClient.Builder
     */
    @JvmStatic
    fun createDefaultBuilder(isDebug: Boolean, context: Context, logger: HttpLoggingInterceptor.Logger): OkHttpClient.Builder {
        val allDebugInterceptors = getAllDebugInterceptors(isDebug, context, logger)
        return createDefaultBuilder(allDebugInterceptors)
    }


    /**
     * 默认网络调试工具 Interceptor
     * @param isDebug Boolean
     * @param context Context
     * @return MutableList<Interceptor>
     */
    @JvmStatic
    fun getAllDebugInterceptors(isDebug: Boolean, context: Context, logger: HttpLoggingInterceptor.Logger): MutableList<Interceptor> {
        return if (!isDebug) {
            mutableListOf()
        } else {
            mutableListOf<Interceptor>().apply {
                Stetho.initializeWithDefaults(context)
                add(StethoInterceptor())
                add(ChuckerInterceptor(context))
                add(OkHttpProfilerInterceptor())
                add(HttpLoggingInterceptor(logger).apply {
                    level = if (isDebug) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                })
            }
        }

    }

}