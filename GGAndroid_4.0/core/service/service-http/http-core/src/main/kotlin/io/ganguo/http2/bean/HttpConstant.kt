package io.ganguo.http2.bean

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc   : Http Common Constant
 * </pre>
 */
class HttpConstant private constructor(){


    /**
     * api common status
     */
    class Status {
        /**
         * @property [SUCCESS] api request data success
         * @property [ERROR] api request data error
         */
        companion object {
            const val SUCCESS = "success"
            const val ERROR = "error"
        }
    }

    /**
     * api common status code
     */
    class Code {

        /**
         * @property [TOKEN_INVALID]  user token invalid
         */
        companion object {
            const val TOKEN_INVALID = 401
            const val GATEWAY_TIMEOUT = 504
        }
    }

}