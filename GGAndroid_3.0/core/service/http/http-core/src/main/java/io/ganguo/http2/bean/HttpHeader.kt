package io.ganguo.http2.bean

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/29
 *     desc   : Http 请求Header通用常量
 * </pre>
 */
class HttpHeader private constructor() {

    object HeaderKey {
        /**
         * Default header params
         */
        const val TOKEN = "Authorization"
        const val FROM = "from"
        const val VERSION = "version"
        const val CHANNEL = "channel"
        const val USER_AGENT = "User-Agent"

    }

    object HeaderValue {
        /**
         * Default header default value
         */
        const val ANDROID = "android"
    }
}