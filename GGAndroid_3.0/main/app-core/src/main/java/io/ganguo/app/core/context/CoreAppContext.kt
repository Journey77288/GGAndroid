package io.ganguo.app.core.context

import io.ganguo.app.core.context.initializer.RequisiteApplicationInitializer
import io.ganguo.app.core.context.initializer.SmartRefreshApplicationInitializer
import io.ganguo.app.core.context.initializer.X5WebApplicationInitializer
import io.ganguo.core.context.BaseApp
import io.ganguo.core.context.initializer.ApplicationInitializer

/**
 * AppCommonContext
 * Created by leo on 2020/06/06.
 */
abstract class CoreAppContext : BaseApp() {

    /**
     * 1、添加[ApplicationInitializer]对应接口实现类
     * PS:切记此处不可直接初始化项目模块或者sdk，因为onCreate函数还未跑，可能会导致模块初始化失败
     */
    override fun applicationInitializer() {
        super.applicationInitializer()
        addInitializer(RequisiteApplicationInitializer.create())
        addInitializer(X5WebApplicationInitializer.create())
        addInitializer(SmartRefreshApplicationInitializer.create())
    }

}
