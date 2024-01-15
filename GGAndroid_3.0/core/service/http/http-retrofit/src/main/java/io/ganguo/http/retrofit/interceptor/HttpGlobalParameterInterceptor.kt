package io.ganguo.http.retrofit.interceptor

import io.ganguo.http.retrofit.RetrofitService
import okhttp3.HttpUrl

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/04
 *     desc   : 全局参数追加拦截器
 * </pre>
 */
class HttpGlobalParameterInterceptor : HttpParameterInterceptor() {
    /**
     * 全局参数，针对所有api
     * @param url HttpUrl
     * @return Map<String, Any>
     */
    override fun getParameters(url: HttpUrl): Map<String, Any> {
        return RetrofitService.get().getGlobalParameters()
    }
}