package io.ganguo.google

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import io.ganguo.sample.sdk.OpenHelper
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.exception.OpenServiceException

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/05
 *     desc   : Google相关工具类
 * </pre>
 */
class GoogleHandler {
    companion object {
        /**
         * 检测Google配置是否正常
         * @param context 上下文
         * @return
         */
        @JvmStatic
        fun checkException(context: Context): Throwable? {
            var exception: OpenServiceException? = null
            if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) != ConnectionResult.SUCCESS) {
                val msg = "Google service is not available"
                val errorCode = GoogleConstants.ErrorCode.GOOGLE_SERVICE_NOT_INSTALLED
                exception = OpenServiceException(OpenChannel.GOOGLE, errorCode, msg)
            }
            return exception
        }
    }
}
