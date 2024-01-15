package io.ganguo.app.helper.activity

import android.app.Activity
import  androidx.activity.ComponentActivity
import androidx.lifecycle.*
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
object ActivityHelper : ActivityObtain<Activity> by handler {

    /**
     * Close Class<ComponentActivity> outside ComponentActivity
     * @param clazz Class<out Activity>
     * @return Boolean
     */
    fun finishOtherActivity(clazz: Class<out Activity>) {
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
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                activity.lifecycle.removeObserver(this)
                handler.remove(activity)
            }

            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                handler.add(activity)
            }
        })
    }

    /**
     * check whether Activity class exist
     *
     * @param clazz Class<out Activity>
     * @return Boolean
     */
    fun isActivityExist(clazz: Class<out Activity>): Boolean {
        var isExist = false
        handler.forEach {
            if (it.javaClass == clazz) {
                isExist = true
                return@forEach
            }
        }
        return isExist
    }
}
