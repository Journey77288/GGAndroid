package io.ganguo.facebook

import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.exception.OpenServiceException

/**
 * <pre>
 *     author : leo
 *     time   : 2020/03/06
 *     desc   : FaceBook SDK工具类
 * </pre>
 */
class FacebookHandler private constructor() {

    companion object {
        /**
         * 检测Facebook SDK 配置是否正常
         * @return
         */
        @JvmStatic
        fun checkException(): Throwable? {
            var exception: OpenServiceException? = null
            if (!FacebookConstants.FACEBOOK_SDK_IS_INIT) {
                val msg = "Please initialize Facebook sdk in Application!!!"
                val errorCode = FacebookConstants.ErrorCode.FACEBOOK_SDK_NOT_INIT
                exception = OpenServiceException(OpenChannel.FACE_BOOK, errorCode, msg)
            }
            return exception
        }
    }
}