package io.ganguo.http.retrofit.interceptor

import io.ganguo.http.retrofit.RetrofitService
import okhttp3.HttpUrl

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/04
 *     desc   : 根据域名，添加对应全局Header参数
 * </pre>
 */
class HttpMultiUrlGlobalHeaderInterceptor : HttpHeaderInterceptor() {

    /**
     * GlobalHeaders
     * @return Map<String, Any>
     */
    override fun getGlobalHeaders(url: HttpUrl): Map<String, Any>? {
        if (!RetrofitService.get().isSupportMultiBaseUrl()) {
            return null
        }
        return RetrofitService.get()
                .getMultiUrlAdapter()!!
                .getGlobalHeaders(url.host)
    }
}