package io.ganguo.http.retrofit

import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

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
    fun createBuilder(): OkHttpClient.Builder = let {
        OkHttpClient.Builder().connectionSpecs(
                listOf(
                        ConnectionSpec.COMPATIBLE_TLS,
                        ConnectionSpec.CLEARTEXT
                )
        )
    }


    /**
     * create default Builder
     * @param interceptors MutableList<Interceptor>
     * @return OkHttpClient.Builder
     */
    @JvmStatic
    fun createOkHttpClientBuilder(vararg interceptors: Interceptor) = let {
        createBuilder().apply {
            interceptors.forEach {
                addInterceptor(it)
            }
            // 配置请求超时时长
            connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
        }
    }
}
