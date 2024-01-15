package io.ganguo.http.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/29
 *     desc   : Builder 参数追加 Interceptor
 * </pre>
 */
interface HttpHeaderInterceptor : Interceptor {

    /**
     * init intercept
     *
     * @param chain Interceptor.Chain
     * @return Response
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = let {
        val headers = getHeaders(chain)
        val request = if (headers.isNullOrEmpty()) {
            chain.request()
        } else {
            val builder = chain.request().newBuilder()
            headers.forEach {
                builder.addHeader(it.key, it.value.toString())
            }
            builder.build()
        }
        chain.proceed(request)
    }


    /**
     * 获取Header参数配置
     * @return HashMap<String,String>
     */
    fun getHeaders(chain: Interceptor.Chain): Map<String, Any>
}