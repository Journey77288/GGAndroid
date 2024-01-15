package io.ganguo.http.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/04
 *     desc   : Get请求，公共参数添加拦截器
 * </pre>
 */
abstract class HttpGetMethodParameterInterceptor : Interceptor {

    /**
     * 通过拦截器添加全局参数
     * @param chain Chain
     * @return Response
     */
    override fun intercept(chain: Interceptor.Chain): Response = let {
        val parameters = getParameters(chain)
        if (parameters.isNullOrEmpty()) {
            chain.proceed(chain.request())
        } else {
            val newUrlBuilder = chain.request()
                    .url
                    .newBuilder()

            parameters.forEach {
                newUrlBuilder.addQueryParameter(it.key, it.value.toString())
            }
            val request = chain.request()
                    .newBuilder()
                    .url(newUrlBuilder.build())
                    .build()
            chain.proceed(request)
        }
    }


    /**
     * 获取app全局参数配置
     * @return Map<String,Any>
     */
    abstract fun getParameters(chain: Interceptor.Chain): Map<String, Any>


}