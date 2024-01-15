package io.ganguo.pay.wxpay

/**
 * Created by Roger on 06/07/2017.
 */

class WXConstants {

    /**
     * @property [WX_APP_ID] 微信开发平台上的AppId
     * @property [WX_SDK_IS_INIT] 微信SDK是否初始化标识
     */
    companion object {
        @JvmStatic
        var WX_APP_ID: String = ""
        var WX_SDK_IS_INIT = false
    }

    /**
     * @property [WX_PAY_NO_RESPONSE_CODE] 没有收到微信支付回调
     * @property [WX_PAY_APP_NOT_INSTALLED] 手机中没有安装微信
     * @property [WX_PAY_SDK_NOT_INIT] 本地SDK未配置微信开放平台的的AppId
     */
    object ErrorCode {
        const val WX_PAY_NO_RESPONSE_CODE = -3
        const val WX_PAY_APP_NOT_INSTALLED = -2
        const val WX_PAY_SDK_NOT_INIT = -1
    }

}
