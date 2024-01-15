package io.ganguo.http.use

import io.ganguo.http.use.creator.ApiServiceCreator
import io.ganguo.http2.config.HttpSetting

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/02
 *     desc   : 网络请求应用管理
 * </pre>
 */
class HttpUse private constructor(var config: HttpUseConfigBuilder) {


    /**
     * <pre>
     *     author : leo
     *     time   : 2020/07/02
     *     desc   : 网络请求应用配置
     * </pre>
     */
    class HttpUseConfigBuilder(var creator: ApiServiceCreator) {
        private var authorizationFailHandle: (() -> Unit)? = null

        fun setAuthorizationFailHandle(handle: (() -> Unit)): HttpUseConfigBuilder {
            this.authorizationFailHandle = handle
            return this
        }

        fun getAuthorizationFailHandle(): (() -> Unit)? {
            return authorizationFailHandle
        }

        fun build(): HttpUse {
            return HttpUse(this)
        }
    }

    /**
     * @property instance 单例对象
     */
    companion object {
        private lateinit var instance: HttpUse

        /**
         * 网络应用层配置
         * @param builder Builder
         */
        @JvmStatic
        fun initialize(httpSetting: HttpSetting, builder: HttpUseConfigBuilder) {
            check(!::instance.isInitialized) { "Already initialized" }
            httpSetting.setting()
            instance = builder.build()
        }


        /**
         * 初始化检测
         */
        private fun checkInit() {
            check(::instance.isInitialized) {
                "You have to initialize HttpUse in Application first!!"
            }
        }

        /**
         * 单例对象
         */
        @JvmStatic
        fun get(): HttpUse {
            checkInit()
            return instance
        }

        /**
         * 获取用户应用层配置
         * @return HttpUseConfig
         */
        @JvmStatic
        internal fun getConfig(): HttpUseConfigBuilder {
            return get().config
        }
    }

}
