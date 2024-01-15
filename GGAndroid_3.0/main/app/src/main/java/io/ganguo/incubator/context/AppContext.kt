package io.ganguo.incubator.context

import io.ganguo.incubator.AppEnv
import io.ganguo.incubator.context.initializer.DatabaseApplicationInitializer
import io.ganguo.incubator.context.initializer.HttpApplicationInitializer


/**
 * CoreAppContext
 * Created by leo on 2020/06/06.
 */
class AppContext : io.ganguo.app.core.context.CoreAppContext() {

    override fun applicationInitializer() {
        super.applicationInitializer()
        addInitializer(DatabaseApplicationInitializer.create(AppEnv.isDebug))
        addInitializer(HttpApplicationInitializer.create())
    }


    companion object {
        fun get(): AppContext {
            return me()
        }
    }

}