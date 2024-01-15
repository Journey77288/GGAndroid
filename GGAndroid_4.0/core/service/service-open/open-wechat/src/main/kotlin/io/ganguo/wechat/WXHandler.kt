package io.ganguo.wechat

import android.content.Context
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.ganguo.factory.isWeChatInstalled
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.exception.OpenServiceException

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 微信处理工具类
 * </pre>
 */
class WXHandler {
    companion object {
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
            } else if (!isInstalled(context)) {
                //app未安装时，抛运行时异常
                val msg = "Please install the WeChat first!!!"
                val errorCode = WXConstants.ErrorCode.WX_PAY_APP_NOT_INSTALLED
                OpenServiceException(OpenChannel.WE_CHAT, errorCode, msg, null)
            } else {
                null
            }
        }

        /**
         * 判断是否已经安装微信
         *
         * @param context Context
         * @return Boolean
         */
        private fun isInstalled(context: Context): Boolean {
            val wxApi = WXAPIFactory.createWXAPI(context, WXConstants.WX_APP_ID)
            val isWXAppInstalled = wxApi.isWXAppInstalled
            return if (isWXAppInstalled) {
                true
            } else {
                isWeChatInstalled(context)
            }
        }
    }
}
