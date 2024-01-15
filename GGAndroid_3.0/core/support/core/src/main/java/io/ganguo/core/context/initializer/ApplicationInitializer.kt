package io.ganguo.core.context.initializer

import android.app.Application

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : Application 配置 初始化接口
 * </pre>
 */
interface ApplicationInitializer {
    /**
     * initialize config
     * @param application Application
     */
    fun initialize(application: Application)
}

