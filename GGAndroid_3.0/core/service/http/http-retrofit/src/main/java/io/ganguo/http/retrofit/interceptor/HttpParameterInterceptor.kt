package io.ganguo.http.retrofit.interceptor

import android.util.Log
import io.ganguo.http2.bean.HttpMethod
import okhttp3.*

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/04
 *     desc   : 全局参数添加拦截器
 * </pre>
 */
abstract class HttpParameterInterceptor : HttpAddedBuilderInterceptor {


    /**
     * 进行参数
     * @param chain Chain
     * @param builder Builder
     * @return Response
     */
    override fun sendRequest(chain: Interceptor.Chain, builder: Request.Builder): Response {
        val sendOldRequest: () -> Response = {
            super.sendRequest(chain, builder)
        }
        val request: Request = chain.request()
        val parameters = getParameters(request.url)
        return when (request.method) {
            HttpMethod.GET.value -> {
                addGetMethodParameter(parameters, chain, builder)
            }
            HttpMethod.POST.value -> {
                addPostMethodParameter(parameters, chain, builder)
            }
            else -> {
                sendOldRequest.invoke()
            }
        }
    }


    /**
     * 为GET请求添加全局参数
     * @param chain Chain
     * @param builder Builder
     * @return Response
     */
    private fun addGetMethodParameter(parameters: Map<String, Any>?, chain: Interceptor.Chain, builder: Request.Builder): Response {
        val urlBuilder: HttpUrl.Builder = chain.request().url.newBuilder()
        return parameters
                ?.forEach {
                    addGetParameter(urlBuilder, it)
                }
                .let {
                    builder.url(urlBuilder.build())
                    proceed(chain, builder)
                }
    }


    /**
     * 为POST请求追加全局参数
     * @param chain Chain
     * @param builder Builder
     * @return Response
     */
    private fun addPostMethodParameter(parameters: Map<String, Any>?, chain: Interceptor.Chain, builder: Request.Builder): Response {
        val bodyBuilder: FormBody.Builder = copyOldParameter(chain.request())
        return parameters
                ?.forEach {
                    addPostParameter(bodyBuilder, it)
                }.let {
                    builder.post(bodyBuilder.build())
                    proceed(chain, builder)
                }
    }


    /**
     * 复制保存原有参数
     * @param request Request
     * @return FormBody.Builder
     */
    private fun copyOldParameter(request: Request): FormBody.Builder {
        val bodyBuilder: FormBody.Builder = FormBody.Builder()
        val body = request.body
        if (body is FormBody) {
            for (i in 0 until body.size) {
                val key = body.name(i)
                val value = body.value(i)
                bodyBuilder.add(key, value)
                val encodedKey = body.encodedName(i)
                val encodedValue = body.encodedValue(i)
                bodyBuilder.addEncoded(encodedKey, encodedValue)
            }
        }
        return bodyBuilder
    }


    /**
     * 对Get请求，追加全局参数
     * @param urlBuilder Builder
     * @param entry Entry<String, Any>
     */
    protected open fun addGetParameter(urlBuilder: HttpUrl.Builder, entry: Map.Entry<String, Any>) {
        urlBuilder.addEncodedQueryParameter(entry.key, entry.value.toString())
        urlBuilder.addQueryParameter(entry.key, entry.value.toString())
    }


    /**
     * 对Post请求，追加全局参数
     * @param bodyBuilder Builder
     * @param entry Entry<String, Any>
     */
    protected open fun addPostParameter(bodyBuilder: FormBody.Builder, entry: Map.Entry<String, Any>) {
        bodyBuilder.addEncoded(entry.key, entry.value.toString())
        bodyBuilder.add(entry.key, entry.value.toString())
    }


    /**
     * 获取app全局参数配置
     * @return Map<String,Any>
     */
    abstract fun getParameters(url: HttpUrl): Map<String, Any>?

}