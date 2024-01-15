package io.ganguo.core.helper.activity

import  androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.util.*

/**
 * Activity管理工具
 */
private val handler: ActivityHandler by lazy {
    ActivityHandler(Stack())
}

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/18
 *     desc   : App Activity Manager
 * </pre>
 */
object ActivityHelper : ActivityObtain<ComponentActivity> by handler {

    /**
     * Close Class<ComponentActivity> outside ComponentActivity
     * @param clazz Class<out Activity>
     * @return Boolean
     */
    fun finishOtherActivity(clazz: Class<out ComponentActivity>) {
        handler
                .filter {
                    it.javaClass != clazz
                }.forEach {
                    it.finish()
                }
    }

    /**
     * bind ComponentActivity to AppManager
     * @param activity ComponentActivity
     */
    fun bindLifecycle(activity: ComponentActivity) {
        activity.lifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                handler.remove(activity)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate() {
                handler.add(activity)
            }

        })
    }


}