package io.ganguo.alipay

import android.content.Context
import io.ganguo.factory.isAppInstalled
import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.exception.OpenServiceException

/**
 * <pre>
 *     author : leo
 *     time   : 2020/03/06
 *     desc   :
 * </pre>
 */
class AliPayHandler {

    companion object {
        //支付宝
        private const val PACKAGE_ALIPAY = "com.eg.android.AlipayGphone"

        /**
         * 检测支付宝SDK配置是否正常
         * @return
         */
        @JvmStatic
        fun checkException(context: Context): OpenServiceException? {
            var throwable: OpenServiceException? = null
            if (!isAppInstalled(context, PACKAGE_ALIPAY)) {
                val msg = "Alipay is not installed"
                val errorCode = AliPayConstants.ErrorCode.ALIPAY_NOT_INSTALLED
                throwable = OpenServiceException(OpenChannel.ALI_PAY, errorCode, msg)
            }
            return throwable
        }
    }
}