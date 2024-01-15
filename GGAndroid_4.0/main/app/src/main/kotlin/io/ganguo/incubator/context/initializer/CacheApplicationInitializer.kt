package io.ganguo.incubator.context.initializer

import android.app.Application
import android.util.Log
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import io.ganguo.cache.database.box.BoxHelper
import io.ganguo.cache.sp.SPHelper
import io.ganguo.incubator.BuildConfig
import io.ganguo.incubator.database.table.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.android.Admin

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : ObjectBox Database and SharedPreferences initialize
 * </pre>
 */
class CacheApplicationInitializer private constructor() : ApplicationInitializer {

    /**
     * Initialize in Application
     * @param application Application
     */
    override fun initialize(application: Application) {
        SPHelper.initialize(application)
        objectBoxInitialize(application)
    }


    /**
     * 检查初始化状态：
     *      1、可以通过输入`ObjectBrowser`,筛选查看控制台是否有打印ObjectBrowser started等数据，有则表示启用成功。
     *
     * 数据库调试：
     *      1、在终端输入：adb forward tcp:8090 tcp:8090
     *      2、在浏览器打开：http://localhost:8090/index.html 即可查看手机数据库数据
     * @param application Application
     */
    private fun objectBoxInitialize(application: Application) {
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
        if (BuildConfig.DEBUG) {
            val isOpen = Admin(boxStore).start(context)
            Log.e(javaClass.simpleName, "AndroidObjectBrowser start is $isOpen")
        }
    }

    companion object : ApplicationInitializerCreator<CacheApplicationInitializer> {
        private const val DATA_BASE_NAME = "object.box."


        /**
         * create DatabaseApplicationInitializer
         * @return DatabaseApplicationInitializer
         */
        override fun createInitializer(parameter: Map<String, Any>): CacheApplicationInitializer {
            return CacheApplicationInitializer()
        }
    }
}
