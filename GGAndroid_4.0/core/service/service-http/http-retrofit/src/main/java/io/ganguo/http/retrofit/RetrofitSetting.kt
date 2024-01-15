package io.ganguo.http.retrofit

import io.ganguo.http2.config.HttpSetting
import okhttp3.OkHttpClient

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/29
 *     desc   : Retrofit网络请求配置
 * </pre>
 */
interface RetrofitSetting : HttpSetting {

    /**
     * 默认配置
     */
    override fun setting() {
        val serviceBuilder = createRetrofitBuilder()
        val httpBuilder = createHttpClientBuilder()
        serviceBuilder
                .build(httpBuilder)
                .run {
                    RetrofitService.init(this)
                }
    }


    /**
     * 创建 RetrofitService 配置
     * @return RetrofitService.Builder
     */
    fun createRetrofitBuilder(): RetrofitService.ConfigBuilder


    /**
     * 配置OkHttp网络请求配置
     * @return OkHttpClient.Builder
     */
    fun createHttpClientBuilder(): OkHttpClient.Builder
}