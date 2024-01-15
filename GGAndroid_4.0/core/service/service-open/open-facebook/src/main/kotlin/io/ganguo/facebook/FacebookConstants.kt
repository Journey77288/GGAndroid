package io.ganguo.facebook

import com.facebook.CallbackManager

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/01
 *     desc   :
 * </pre>
 * @property callbackManager 结果回调管理对象
 */
class FacebookConstants {

    /**
     * @property [FACEBOOK_SDK_IS_INIT] FaceBook Sdk是否有初始化
     */
    companion object {
        @JvmStatic
        internal var callbackManager: CallbackManager? = null
        @JvmStatic
        internal var FACEBOOK_SDK_IS_INIT = false
    }

    /**
     * 错误码
     * @property [FACEBOOK_SDK_NOT_INIT] Facebook SDK未初始化
     */
    object ErrorCode {
        internal const val FACEBOOK_SDK_NOT_INIT = -2
    }
}