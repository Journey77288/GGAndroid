package io.ganguo.http.retrofit.interceptor

import io.ganguo.http.retrofit.RetrofitService
import okhttp3.Interceptor

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/04
 *     desc   : 根据api域名，添加通用参数
 * </pre>
 */
class HttpMultiUrlGlobalParameterInterceptor : HttpGetMethodParameterInterceptor() {
    /**
     * 根据url获取对应的参数配置
     * @param chain Chain
     * @return Map<String, Any>
     */
    override fun getParameters(chain: Interceptor.Chain): Map<String, Any> = let{
        val url=chain.request().url
        if (!RetrofitService.get().isSupportMultiBaseUrl()) {
            emptyMap()
        } else {
            RetrofitService
                    .get()
                    .getMultiUrlAdapter()!!
                    .getGlobalParameters(url.host)
        }

    }
}