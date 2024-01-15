package io.ganguo.wechat

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 参数配置
 * </pre>
 */
class WXConstants {

    /**
     * @property WX_APP_ID 微信开放平台AppId
     * @property WX_SDK_IS_INIT 微信SDK是否初始化
     */
    companion object {
        @JvmStatic
        var WX_SDK_IS_INIT = false
        @JvmStatic
        var WX_APP_ID: String = ""
        const val WE_CHAT_SCOPE = "snsapi_userinfo"
        const val WE_CHAT_SDK_DEMO_TEST = "wechat_sdk_demo_test"
    }

    /**
     * @property [WX_PAY_NO_RESPONSE_CODE] 没有收到微信支付回调
     * @property [WX_PAY_APP_NOT_INSTALLED] 手机中没有安装微信
     * @property [WX_PAY_SDK_NOT_INIT] 微信SDK未在Application中初始化
     */
    object ErrorCode {
        const val WX_PAY_NO_RESPONSE_CODE = -1
        const val WX_PAY_APP_NOT_INSTALLED = -2
        const val WX_PAY_SDK_NOT_INIT = -3
    }
}