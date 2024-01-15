package io.ganguo.wechat

import android.content.Context
import io.ganguo.factory.isAppInstalled
import io.ganguo.open.sdk.OpenHelper
import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.exception.OpenServiceException

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 微信处理工具类
 * </pre>
 */
class WXHandler {
    companion object {
        private const val PACKAGE_WECHAT = "com.tencent.mm"

        /**
         * 微信SDK异常检测
         * @return Throwable?
         */
        fun checkException(context: Context): Throwable? {
            return if (!WXConstants.WX_SDK_IS_INIT) {
                //appId为空时，抛异常
                val msg = "Please initialize WeChat sdk in Application!!!"
                val errorCode = WXConstants.ErrorCode.WX_PAY_SDK_NOT_INIT
                OpenServiceException(OpenChannel.WE_CHAT, errorCode, msg, null)
            } else if (!isAppInstalled(context, PACKAGE_WECHAT)) {
                //app未安装时，抛运行时异常
                val msg = "Please install the WeChat first!!!"
                val errorCode = WXConstants.ErrorCode.WX_PAY_APP_NOT_INSTALLED
                OpenServiceException(OpenChannel.WE_CHAT, errorCode, msg, null)
            } else {
                null
            }
        }


    }
}