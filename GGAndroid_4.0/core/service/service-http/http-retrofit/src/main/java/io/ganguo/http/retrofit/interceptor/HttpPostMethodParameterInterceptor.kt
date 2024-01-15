package io.ganguo.http.retrofit.interceptor

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.Response
import okhttp3.internal.http.HttpMethod

/**
 * <pre>
 *   @author : leo
 *    time   : 2020/08/19
 *    desc   : Post请求，公共参数添加拦截器
 * </pre>
 */
abstract class HttpPostMethodParameterInterceptor : Interceptor {

    /**
     * 通过拦截器添加全局参数
     * @param chain Chain
     * @return Response
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val parameter = getParameters(chain)
        var request = chain.request()
        if (parameter.isNullOrEmpty()) {
            return chain.proceed(request)
        }
        if (!HttpMethod.requiresRequestBody(request.method)) {
            return chain.proceed(chain.request())
        }
        var body = request.body
        if (body is FormBody) {
            val bodyBuilder = FormBody.Builder()
            var formBody = request.body as FormBody
            for (index in 0 until formBody.size) {
                bodyBuilder.addEncoded(formBody.encodedName(index), formBody.encodedValue(index))
            }
            parameter.forEach {
                bodyBuilder.add(it.key, it.value.toString())
            }
            body = bodyBuilder.build()
            request = request
                    .newBuilder()
                    .post(body)
                    .build()
        } else if (body is MultipartBody) {
            val oldPartList = body.parts
            val multiPartBuilder = MultipartBody.Builder()
            multiPartBuilder.setType(MultipartBody.FORM)
            for (part in oldPartList) {
                multiPartBuilder.addPart(part)
            }
            parameter.forEach {
                multiPartBuilder.addFormDataPart(it.key, it.value.toString())
            }
            body = multiPartBuilder.build()
            request = request
                    .newBuilder()
                    .post(body)
                    .build()
        }
        return chain.proceed(request)
    }

    /**
     * 获取app全局参数配置
     * @return Map<String,Any>
     */
    abstract fun getParameters(chain: Interceptor.Chain): Map<String, Any>

}