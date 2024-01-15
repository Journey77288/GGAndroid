package io.ganguo.twitter

/**
 * <pre>
 *     author : leo
 *     time   : 2020/03/06
 *     desc   : 新浪微博SDK相关常量
 * </pre>
 */
class TwitterConstants private constructor() {

    /**
     * @property [TWITTER_SDK_IS_INIT] SDK是否初始化标识
     */
    companion object {
        @JvmStatic
        var TWITTER_SDK_IS_INIT = false
    }

    /**
     * 错误码
     * @property [TWITTER_NOT_INSTALLED] 手机未安装Twitter应用
     * @property [TWITTER_NOT_INIT] Twitter Sdk尚未初始化
     */
    object ErrorCode {
        const val TWITTER_NOT_INSTALLED = -1
        const val TWITTER_NOT_INIT = -2
    }

}