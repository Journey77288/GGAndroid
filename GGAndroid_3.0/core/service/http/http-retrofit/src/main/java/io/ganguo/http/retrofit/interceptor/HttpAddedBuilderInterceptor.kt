package io.ganguo.http.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/29
 *     desc   : Builder 参数追加 Interceptor
 * </pre>
 */
interface HttpAddedBuilderInterceptor : Interceptor {
    /**
     * new Request.Builder
     *
     * @param chain Interceptor.Chain
     * @return Request.Builder
     */
    fun newBuilder(chain: Interceptor.Chain): Request.Builder {
        return chain.request().newBuilder()
    }

    /**
     * init intercept
     *
     * @param chain Interceptor.Chain
     * @return Response
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return sendRequest(chain)
    }

    /**
     * init Response
     *
     * @param chain Interceptor.Chain
     * @return Response
     */
    @Throws(IOException::class)
    fun sendRequest(chain: Interceptor.Chain): Response {
        return newBuilder(chain)
                .let {
                    addedBuilder(chain, it)
                    sendRequest(chain, it)
                }
    }

    /**
     * 对 [Request.Builder]追加参数
     *
     * @param builder Request.Builder
     * @param chain Interceptor.Chain
     * @return Response
     */
    fun sendRequest(chain: Interceptor.Chain, builder: Request.Builder): Response {
        return proceed(chain, builder)
    }

    /**
     * 进行网络请求
     *
     * @param builder Request.Builder
     * @param chain Interceptor.Chain
     * @return Response
     */
    fun proceed(chain: Interceptor.Chain, builder: Request.Builder): Response {
        return chain.proceed(builder.build())
    }


    /**
     * 对 [Request.Builder] 追加参数
     *
     * @param builder Request.Builder
     * @return
     */
    fun addedBuilder(chain: Interceptor.Chain, builder: Request.Builder) {

    }

}