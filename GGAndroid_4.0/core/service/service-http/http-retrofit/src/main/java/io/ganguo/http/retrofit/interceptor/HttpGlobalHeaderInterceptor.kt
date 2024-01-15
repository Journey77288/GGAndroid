package io.ganguo.http.retrofit.interceptor

import io.ganguo.http.retrofit.RetrofitService
import okhttp3.Interceptor

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/04
 *     desc   : 全局所有API Header参数配置
 * </pre>
 */
open class HttpGlobalHeaderInterceptor : HttpHeaderInterceptor {


    /**
     * 全局Header参数
     * @param chain Chain
     */
    override fun getHeaders(chain: Interceptor.Chain): Map<String, Any> = let {
        RetrofitService.get().getGlobalHeaders()
    }
}