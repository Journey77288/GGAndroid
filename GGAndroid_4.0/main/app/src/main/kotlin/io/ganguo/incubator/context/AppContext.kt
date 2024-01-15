package io.ganguo.incubator.context

import io.ganguo.core.context.AppCoreContext
import io.ganguo.incubator.context.initializer.SmartRefreshApplicationInitializer
import io.ganguo.incubator.context.initializer.CacheApplicationInitializer
import io.ganguo.incubator.context.initializer.HttpApplicationInitializer


/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : Main AppContext
 * </pre>
 */
class AppContext : AppCoreContext() {

    init {
        APP = this
    }

    override fun applicationInitializer() {
        super.applicationInitializer()
        addInitializer(SmartRefreshApplicationInitializer.create())
        addInitializer(CacheApplicationInitializer.create())
        addInitializer(HttpApplicationInitializer.create())
    }


    companion object {
        private lateinit var APP: AppContext

        /**
         * The global app context
         * @return T
         */
        @JvmStatic
        fun me(): AppContext {
            return APP
        }
    }

}
