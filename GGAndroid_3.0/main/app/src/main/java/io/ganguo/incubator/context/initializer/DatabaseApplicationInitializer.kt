package io.ganguo.incubator.context.initializer

import android.app.Application
import android.util.Log
import io.ganguo.ggcache.database.box.BoxHelper
import io.ganguo.incubator.database.table.MyObjectBox
import io.ganguo.core.context.initializer.ApplicationInitializer
import io.ganguo.core.context.initializer.ApplicationInitializerCreator
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : ObjectBox Database init
 * </pre>
 */
class DatabaseApplicationInitializer private constructor(var debug: Boolean) : ApplicationInitializer {


    /**
     * 检查初始化状态：
     *      1、可以通过输入`ObjectBrowser`,筛选查看控制台是否有打印ObjectBrowser started等数据，有则表示启用成功。
     *
     * 数据库调试：
     *      1、在终端输入：adb forward tcp:8090 tcp:8090
     *      2、在浏览器打开：http://localhost:8090/index.html 即可查看手机数据库数据
     * @param application Application
     */
    override fun initialize(application: Application) {
        MyObjectBox
                .builder()
                .androidContext(application)
                .name(DATA_BASE_NAME + application.packageName)
                .build()
                .apply {
                    BoxHelper.initialize(this)
                    startAndroidObjectBrowser(application, this)
                }

    }

    /**
     * Launch the database browser
     * @param context Application
     * @param boxStore Boolean
     */
    private fun startAndroidObjectBrowser(context: Application, boxStore: BoxStore) {
        if (debug) {
            val isOpen = AndroidObjectBrowser(boxStore).start(context)
            Log.e(javaClass.simpleName, "AndroidObjectBrowser start is $isOpen")
        }
    }

    companion object : ApplicationInitializerCreator<DatabaseApplicationInitializer> {
        private const val KEY_DEBUG = "debug"
        private const val DATA_BASE_NAME = "object.box."


        /**
         * create DatabaseApplicationInitializer
         * @param debug Boolean debug Whether the rider is running in debug mode
         * @return Pair<String, DatabaseApplicationInitializer>
         */
        fun create(debug: Boolean): Pair<String, DatabaseApplicationInitializer> {
            return create(mapOf(
                    KEY_DEBUG to debug
            ))
        }

        override fun create(): Pair<String, DatabaseApplicationInitializer> {
            return create(false)
        }

        /**
         * create DatabaseApplicationInitializer
         * @return DatabaseApplicationInitializer
         */
        override fun createInitializer(parameter: Map<String, Any>): DatabaseApplicationInitializer {
            var debug = parameter[KEY_DEBUG]
            return if (debug == null) {
                false
            } else {
                debug as Boolean
            }.let {
                DatabaseApplicationInitializer(it)
            }
        }
    }
}