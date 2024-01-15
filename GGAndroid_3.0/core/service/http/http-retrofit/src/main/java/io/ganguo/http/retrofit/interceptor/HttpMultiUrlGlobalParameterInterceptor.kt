package io.ganguo.http.retrofit.interceptor

import io.ganguo.http.retrofit.RetrofitService
import okhttp3.HttpUrl

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/04
 *     desc   : 根据api域名，添加通用参数
 * </pre>
 */
class HttpMultiUrlGlobalParameterInterceptor : HttpParameterInterceptor() {

    override fun getParameters(url: HttpUrl): Map<String, Any>? {
        if (!RetrofitService.get().isSupportMultiBaseUrl()) {
            return null
        }
        return RetrofitService.get()
                .getMultiUrlAdapter()!!
                .getGlobalParameters(url.host)
    }
}