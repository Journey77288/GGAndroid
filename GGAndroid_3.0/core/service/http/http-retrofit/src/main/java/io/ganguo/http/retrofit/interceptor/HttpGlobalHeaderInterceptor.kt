package io.ganguo.http.retrofit.interceptor

import io.ganguo.http.retrofit.RetrofitService
import okhttp3.HttpUrl

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/04
 *     desc   : 全局所有API Header参数配置
 * </pre>
 */
class HttpGlobalHeaderInterceptor : HttpHeaderInterceptor() {
    /**
     * GlobalHeaders
     * @return Map<String, Any>
     */
    override fun getGlobalHeaders(url: HttpUrl): Map<String, Any>? {
        return RetrofitService.get().getGlobalHeaders()
    }
}