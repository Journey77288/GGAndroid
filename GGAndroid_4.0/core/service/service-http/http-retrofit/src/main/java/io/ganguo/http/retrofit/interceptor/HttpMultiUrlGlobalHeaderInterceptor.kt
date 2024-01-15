package io.ganguo.http.retrofit.interceptor

import io.ganguo.http.retrofit.RetrofitService
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/04
 *     desc   : 根据域名，添加对应全局Header参数
 * </pre>
 */
class HttpMultiUrlGlobalHeaderInterceptor : HttpHeaderInterceptor {

    /**
     * 根据Url取出对应的Header参数配置
     * @return Map<String, Any>
     */
    override fun getHeaders(chain: Interceptor.Chain): Map<String, Any> = let {
        val url = chain.request().url
        if (!RetrofitService.get().isSupportMultiBaseUrl()) {
            emptyMap()
        } else {
            RetrofitService.get()
                    .getMultiUrlAdapter()!!
                    .getGlobalHeaders(url.host)
        }
    }
}