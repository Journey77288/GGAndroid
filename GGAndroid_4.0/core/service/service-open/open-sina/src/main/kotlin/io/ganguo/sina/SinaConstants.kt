package io.ganguo.sina

/**
 * <pre>
 *     author : leo
 *     time   : 2020/03/06
 *     desc   : 新浪微博SDK相关常量
 * </pre>
 */
class SinaConstants private constructor() {


    /**
     * 错误码
     * @property [SINA_SDK_NOT_INIT] 新浪微博SDK未初始化
     * @property [SINA_CLIENT_NOT_INSTALL] 新浪微博客户端未初始化
     */
    object ErrorCode {
        const val SINA_SDK_NOT_INIT = -2
        const val SINA_CLIENT_NOT_INSTALL = -1
    }

}