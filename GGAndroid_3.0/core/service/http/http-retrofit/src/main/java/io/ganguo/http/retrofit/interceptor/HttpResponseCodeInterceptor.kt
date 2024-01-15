package io.ganguo.http.retrofit.interceptor

import io.ganguo.http2.error.Errors
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc1   : 常见Http Response code 拦截处理
 *     desc2   : Response Code规范: @see <a href="https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status">Response code规范文档</a>
 * </pre>
 *
 * 说明：
 *     1、按规范，正常只有code==200时，才视为请求成功，其余均为失败
 *     2、如接口返回response code不规范，在code!=200的情况，返回了数据，则可以调用[createResponse]函数，注意将新请求response code设为200，
 *     返回正常的Response，进入正常解析接口数据流程。
 */
open class HttpResponseCodeInterceptor : Interceptor {

    /**
     * 拦截接口响应码
     * @param chain Chain
     * @return Response
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val code = response.code
        if (isClientRequestFailure(code)) {
            throw Errors.ClientRequestException(code, response.message)
        }
        if (isServerResponseFailure(code)) {
            throw Errors.ServerResponseException(code, response.message)
        }
        return response
    }


    /**
     * 创建一个新的Response对象
     *
     * @param chain Interceptor.Chain
     * @param responseCode Int
     * @param body ResponseBody
     * @return Response
     */
    open fun createResponse(responseCode: Int = 200, chain: Interceptor.Chain, body: ResponseBody): Response {
        return Response.Builder()
                .code(responseCode)
                .body(body)
                .request(chain.request())
                .message("")
                .protocol(Protocol.HTTP_2)
                .build()
    }


    /**
     * 服务器响应失败
     * @param code Int
     * @return Boolean
     */
    private fun isServerResponseFailure(code: Int): Boolean {
        if (code in 500..511) {
            return true
        }
        return false
    }

    /**
     * 客户端发送请求错误
     * @param code Int
     * @return Boolean
     */
    private fun isClientRequestFailure(code: Int): Boolean {
        if (code in 400..451) {
            return true
        }
        return false
    }
}