package io.ganguo.qq

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : QQ SDK相关常量
 * </pre>
 * @property [QQ_APP_ID] QQ开放平台AppID
 * @property [QQ_SDK_IS_INIT] QQ SDK 是否初始化标识
 */
class QQConstants {
    companion object {
        @JvmStatic
        var QQ_APP_ID: String = ""
        var QQ_SDK_IS_INIT: Boolean = false
        const val OPENID = "openid"
        const val ACCESS_TOKEN = "access_token"
        const val EXPIRES_IN = "expires_in"
        const val ALL: String = "all"
    }


    /**
     * 错误码
     * @property [QQ_NOT_INSTALLED] 手机未安装QQ应用
     * @property [QQ_APP_ID_NOT_CONFIGURED] 本地未配置开发平台上的App Key ID
     */
    object ErrorCode {
        const val QQ_NOT_INSTALLED = -1
        const val QQ_APP_ID_NOT_CONFIGURED = -2
    }
}