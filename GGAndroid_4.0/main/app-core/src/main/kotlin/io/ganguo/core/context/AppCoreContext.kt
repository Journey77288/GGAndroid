package io.ganguo.core.context

import io.ganguo.app.context.BaseApplication
import io.ganguo.core.BuildConfig
import io.ganguo.core.context.initializer.*

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : AppCoreContext
 * </pre>
 */
abstract class AppCoreContext : BaseApplication() {
    /**
     * The initializer must be used here for module initialization
     */
    override fun applicationInitializer() {
        super.applicationInitializer()
        if (BuildConfig.DEBUG) {
            addInitializer(DebugToolsApplicationInitializer.create())
        }
        addInitializer(ViewLayerApplicationInitializer.create())
        addInitializer(ServiceApplicationInitializer.create())
        addInitializer(RxJavaApplicationInitializer.create())
        addInitializer(LocationApplicationInitializer.create())
    }
}
