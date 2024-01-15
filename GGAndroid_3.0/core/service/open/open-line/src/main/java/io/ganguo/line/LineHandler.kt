package io.ganguo.line

import android.content.Context
import io.ganguo.factory.isAppInstalled
import io.ganguo.open.sdk.OpenHelper
import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.exception.OpenServiceException

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/05
 *     desc   : Line相关工具类
 * </pre>
 */
class LineHandler private constructor() {

    companion object {
        private const val PACKAGE_LINE = "jp.naver.line.android"

        /**
         * 检测LIne配置是否正常
         * @param context 上下文
         * @return
         */
        @JvmStatic
        fun checkException(context: Context): OpenServiceException? {
            var exception: OpenServiceException? = null
            if (LineConstants.LINE_CHANNEL_ID.isEmpty()) {
                val msg = "Please initialize line sdk in Application!!!"
                val errorCode = LineConstants.ErrorCode.LINE_SDK_NOT_INIT
                exception = OpenServiceException(OpenChannel.LINE, errorCode, msg)
            } else if (!isAppInstalled(context, PACKAGE_LINE)) {
                val msg = "Please install the line first!!!"
                val errorCode = LineConstants.ErrorCode.LINE_NOT_INSTALLED
                exception = OpenServiceException(OpenChannel.LINE, errorCode, msg)
            }
            return exception
        }

    }
}