package io.ganguo.http.retrofit.interceptor

import io.ganguo.http.retrofit.RetrofitService
import okhttp3.Interceptor

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/04
 *     desc   : Get请求，公共参数添加拦截器
 * </pre>
 */
class HttpGlobalGetMethodParameterInterceptor : HttpGetMethodParameterInterceptor() {
    /**
     * 全局参数，针对所有api
     * @param chain Chain
     * @return Map<String, Any>
     */
    override fun getParameters(chain: Interceptor.Chain): Map<String, Any> = let {
        RetrofitService.get().getGlobalParameters()
    }
}