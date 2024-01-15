package io.ganguo.demo.context

import io.ganguo.demo.BuildConfig
import io.ganguo.demo.context.initializer.*
import io.ganguo.core.context.BaseApp

/**
 * GGDemo AppContext
 * Created by leo on 2016/12/21.
 */
class AppContext : BaseApp() {

    init {
        addInitializer(SmartRefreshApplicationInitializer.create())
        addInitializer(DebugApplicationInitializer.create())
        addInitializer(RequisiteApplicationInitializer.create())
        addInitializer(X5WebApplicationInitializer.create())
        addInitializer(DatabaseApplicationInitializer.create(BuildConfig.DEBUG))
        addInitializer(HttpApplicationInitializer.create())
        addInitializer(LanguageApplicationInitializer.create())
    }



    companion object {
        fun get(): AppContext {
            return me()
        }
    }

}
