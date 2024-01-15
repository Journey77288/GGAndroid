package io.ganguo.sina

import android.content.Context
import io.ganguo.sample.sdk.OpenHelper
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.exception.OpenServiceException

/**
 * <pre>
 *     author : leo
 *     time   : 2020/03/06
 *     desc   : 新浪微博工具类
 * </pre>
 */
class SinaHandler private constructor() {

    companion object {
        //新浪微博
        private const val PACKAGE_SINA_WB = "com.sina.weibo"

        /**
         * 检查新浪SDK相关配置参数是否有异常
         * @param context 上下文
         * @throws
         */
        @JvmStatic
        fun checkException(context: Context): OpenServiceException? {
            var exception: OpenServiceException? = null
            try {
                if (!OpenHelper.isAppInstalled(context, PACKAGE_SINA_WB)) {
                    val msg = "Please install Sina Client first!!!"
                    val errorCode = SinaConstants.ErrorCode.SINA_CLIENT_NOT_INSTALL
                    exception = OpenServiceException(OpenChannel.SINA, errorCode, msg)
                }
            } catch (e: Exception) {
                val msg = "Please initialize Sina sdk in Application!!!"
                val errorCode = SinaConstants.ErrorCode.SINA_SDK_NOT_INIT
                exception = OpenServiceException(OpenChannel.SINA, errorCode, msg, e)
            } finally {
                return exception
            }
        }
    }
}
