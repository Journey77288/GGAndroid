package io.ganguo.http.retrofit.interceptor

import android.accounts.NetworkErrorException
import android.util.Log
import io.ganguo.http.retrofit.R
import io.ganguo.http2.bean.HttpConstant.Code.Companion.GATEWAY_TIMEOUT
import io.ganguo.http2.error.Errors
import io.ganguo.resources.ResourcesHelper
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.net.UnknownHostException

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
	override fun intercept(chain: Interceptor.Chain): Response = let {
		var response: Response?
		try {
			response = chain.proceed(chain.request())
			val error = exception(response)
			if (error != null) {
				throw error
			}
			if (isUnauthorizedFailure(response.code)) {
				response = createResponse(chain = chain, body = response.body)
			}
			response
		} catch (e: UnknownHostException) {
            e.printStackTrace()
            throw NetworkErrorException(ResourcesHelper.getString(R.string.str_network_anomaly))
        } catch (e: Throwable) {
			e.printStackTrace()
			throw  e
		}
	}


	/**
	 * 过滤网络请求异常
	 * @param response Response
	 * @return Exception?
	 */
	private fun exception(response: Response): Exception? {
		val code = response.code
        // TODO: 22/01/20 根据需要，设置不同接口访问错误状态下的提示语
        Log.w(javaClass.simpleName, "Request exception，response code is $code!! $response.message")
		return if (isClientRequestFailure(code)) {
		    val msg = ResourcesHelper.getString(R.string.str_client_request_failure)
			Errors.ClientRequestException(code, msg)
		} else if (isServerResponseFailure(code)) {
		    val msg = ResourcesHelper.getString(R.string.str_server_response_failure)
//            if (isTimeoutFailure(code)) {
//                Errors.ServerResponseException(code, "Access timeout")
//            } else {
                Errors.ServerResponseException(code, msg)
//            }
		} else if (!response.isSuccessful) {
		    val msg = ResourcesHelper.getString(R.string.str_server_response_failure)
			Errors.ClientRequestException(code, msg)
		} else {
			null
		}
	}


	/**
	 * Access Token过期
	 * @return Boolean
	 */
	private fun isUnauthorizedFailure(code: Int): Boolean {
		return code == 401
	}


    /**
     * 访问超时
     * @param code Int
     * @return Boolean
     */
    private fun isTimeoutFailure(code: Int): Boolean {
        return code == GATEWAY_TIMEOUT
    }


	/**
	 * 创建一个新的Response对象
	 *
	 * @param chain Interceptor.Chain
	 * @param responseCode Int
	 * @param body ResponseBody
	 * @return Response
	 */
	open fun createResponse(responseCode: Int = 200, chain: Interceptor.Chain, body: ResponseBody?) = let {
		Response.Builder()
			  .code(responseCode)
			  .body(body)
			  .request(chain.request())
			  .message("Request error")
			  .protocol(Protocol.HTTP_2)
			  .build()
	}


	/**
	 * 服务器响应失败
	 * @param code Int
	 * @return Boolean
	 */
	private fun isServerResponseFailure(code: Int) = let {
		code in 500..511
	}

	/**
	 * 客户端发送请求错误
	 * @param code Int
	 * @return Boolean
	 */
	private fun isClientRequestFailure(code: Int) = let {
		code in 402..451
	}
}
