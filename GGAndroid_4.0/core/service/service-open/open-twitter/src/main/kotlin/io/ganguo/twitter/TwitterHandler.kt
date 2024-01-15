package io.ganguo.twitter

import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.exception.OpenServiceException

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/05
 *     desc   : Twitter工具类
 * </pre>
 */
class TwitterHandler {
    companion object {

        /**
         * 检测推特配置是否正常
         * @throws
         */
        @JvmStatic
        fun checkException(): OpenServiceException? {
            var exception: OpenServiceException? = null
            if (!TwitterConstants.TWITTER_SDK_IS_INIT) {
                val msg = "Please initialize Twitter sdk in Application!!!"
                val errorCode = TwitterConstants.ErrorCode.TWITTER_NOT_INIT
                exception = OpenServiceException(OpenChannel.TWITTER, errorCode, msg)
            }
            return exception
        }
    }
}