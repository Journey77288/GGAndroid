package io.ganguo.google

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/02
 *     desc   : Google登录相关常量
 * </pre>
 */
class GoogleConstants {
    /**
     * [GOOGLE_REQUEST_AUTH_CODE] 谷歌的登录认证请求码
     */
    companion object {
        var GOOGLE_REQUEST_AUTH_CODE: Int = 9003
    }

    /**
     * 错误码
     * @property [GOOGLE_SERVICE_NOT_INSTALLED] 本地未安装谷歌服务
     */
    object ErrorCode {
        const val GOOGLE_SERVICE_NOT_INSTALLED = -1
    }
}