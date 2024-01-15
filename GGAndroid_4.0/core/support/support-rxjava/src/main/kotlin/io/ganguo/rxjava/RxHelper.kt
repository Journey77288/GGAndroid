package io.ganguo.rxjava

import io.ganguo.rxjava.port.IRxJavaBuilder
import io.reactivex.rxjava3.plugins.RxJavaPlugins

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/18
 *   @desc   : Rxjava 配置类型
 * </pre>
 */
class RxHelper private constructor(private var builder: IRxJavaBuilder) : IRxJavaBuilder by builder {

    init {
        RxJavaPlugins.setErrorHandler {
            it.printStackTrace()
            builder.logger?.invoke("RxJavaPlugins:ErrorHandler:" + it.message)
        }
    }


    /**
     * RxJava Builder
     * @property toast Function1<String, Unit>?
     * @property throwable Function1<Throwable, Unit>?
     * @property logger Function1<String, Unit>?
     */
    class Builder : IRxJavaBuilder {
        override var toast: ((String) -> Unit)? = null
        override var throwable: ((Throwable) -> Unit)? = null
        override var logger: ((String) -> Unit)? = null

        fun build() = RxHelper(this)
    }


    companion object {
        private lateinit var instance: RxHelper


        /**
         * RxJava 配置
         * @param builder Builder
         */
        fun initialize(builder: Builder) {
            check(!::instance.isInitialized) {
                "Already initialized"
            }
            instance = builder.build()
        }


        /**
         * get RxJava RxHelper
         * @return RxJava
         */
        fun get(): RxHelper = let {
            checkInit()
            instance
        }


        /**
         * 初始化检测
         */
        private fun checkInit() {
            check(::instance.isInitialized) {
                "You have to initialize RxHelper.initialize(Builder) in Application first!!"
            }
        }
    }


}