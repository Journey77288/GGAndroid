package io.ganguo.line

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/04
 *     desc   :
 * </pre>
 */
class LineConstants {

    /**
     * 通用常量
     * [LINE_CHANNEL_ID] 开放平台app channel id
     * [LINE_LOGIN_REQUEST] Line 登录请求码
     * [LINE_SDK_IS_INIT] Line SDK是否初始化标识
     */
    companion object {
        @JvmStatic
        var LINE_CHANNEL_ID: String = ""
        var LINE_SDK_IS_INIT: Boolean = false
        const val LINE_LOGIN_REQUEST = 20012
    }


    /**
     * 错误码
     * @property [LINE_NOT_INSTALLED] 手机未安装Line应用
     * @property [LINE_SDK_NOT_INIT] Line SDK未进行初始化配置
     */
    object ErrorCode {
        const val LINE_NOT_INSTALLED = -1
        const val LINE_SDK_NOT_INIT = -2
    }
}