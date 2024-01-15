package io.ganguo.http2.error

import java.lang.RuntimeException

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc   : 通用异常错误封装
 * </pre>
 * @property [code] Int 错误码
 * @property message String 错误信息
 * @property throwable Throwable 上级异常
 */
sealed class Errors(val code: Int = -1, msg: String = "", throwable: Throwable? = null) : RuntimeException(msg, throwable) {
    /**
     * 连接异常：网络错误、超时
     * @constructor
     */
    class ConnectFailedException(code: Int = -1, msg: String = "", throwable: Throwable? = null) : Errors(code, msg, throwable)

    /**
     * 服务器响应异常：服务器出错，服务器更新，无法正确响应。例如：服务器部署中502异常。
     * @constructor
     */
    class ServerResponseException(code: Int = -1, msg: String = "", throwable: Throwable? = null) : Errors(code, msg, throwable)


    /**
     * 客户端请求异常：客户端请求参数出错，直接导致连接中断之类的操作。例如：接口请求地址出错，404异常。
     * @constructor
     */
    class ClientRequestException(code: Int = -1, msg: String = "", throwable: Throwable? = null) : Errors(code, msg, throwable)


    /**
     * Api响应异常：客户请求正常，且服务器正确响应，但因数据缺少或内部处理出错。例如：对不存在的id数据，进行操作。
     * @constructor
     */
    open class ApiResponseException(code: Int = -1, msg: String = "", throwable: Throwable? = null) : Errors(code, msg, throwable)


    /**
     * 用户Token鉴权失败
     * @constructor
     */
    class AuthorizationException(code: Int = -1, msg: String = "", throwable: Throwable? = null) : ApiResponseException(code, msg, throwable)


}