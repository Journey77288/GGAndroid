package io.ganguo.google

import android.content.Context
import io.ganguo.factory.isAppInstalled
import io.ganguo.open.sdk.OpenHelper
import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.exception.OpenServiceException

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/05
 *     desc   : Google相关工具类
 * </pre>
 */
class GoogleHandler {
    companion object {
        private const val PACKAGE_GMAIL = "com.google.android.gm"

        /**
         * 检测Google配置是否正常
         * @param context 上下文
         * @return
         */
        @JvmStatic
        fun checkException(context: Context): Throwable? {
            var exception: OpenServiceException? = null
            if (!isAppInstalled(context, PACKAGE_GMAIL)) {
                val msg = "Alipay is not installed"
                val errorCode = GoogleConstants.ErrorCode.GOOGLE_SERVICE_NOT_INSTALLED
                exception = OpenServiceException(OpenChannel.GOOGLE, errorCode, msg)
            }
            return exception
        }
    }
}