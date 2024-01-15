package io.ganguo.http.retrofit.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/29
 *     desc   : 全局Header配置
 * </pre>
 */
abstract class HttpHeaderInterceptor : HttpAddedBuilderInterceptor {

    /**
     * 追加全局Header参数
     * @param builder Request.Builder
     */
    override fun addedBuilder(chain: Interceptor.Chain, builder: Request.Builder) {
        getGlobalHeaders(chain.request().url)
                ?.forEach {
                    builder.addHeader(it.key, it.value.toString())
                }
    }


    /**
     * 取出全局Header参数
     * @return MutableMap<String, String>
     */
    abstract fun getGlobalHeaders(url: HttpUrl): Map<String, Any>?


}