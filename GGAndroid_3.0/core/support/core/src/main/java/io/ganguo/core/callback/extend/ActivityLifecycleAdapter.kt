package io.ganguo.core.callback.extend

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : Activity 生命周期
 * </pre>
 */
class ActivityLifecycleAdapter : ActivityLifecycleCallbacks {
    /**
     * 委托对象
     */
    private var delegate: ActivityLifecycleCallbacks? = null
    fun setDelegate(delegate: ActivityLifecycleCallbacks?) {
        this.delegate = delegate
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
        if (delegate != null) {
            delegate!!.onActivityCreated(activity, savedInstanceState)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        if (delegate != null) {
            delegate!!.onActivityStarted(activity)
        }
    }

    override fun onActivityResumed(activity: Activity) {
        if (delegate != null) {
            delegate!!.onActivityResumed(activity)
        }
    }

    override fun onActivityPaused(activity: Activity) {
        if (delegate != null) {
            delegate!!.onActivityPaused(activity)
        }
    }

    override fun onActivityStopped(activity: Activity) {
        if (delegate != null) {
            delegate!!.onActivityStopped(activity)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        if (delegate != null) {
            delegate!!.onActivitySaveInstanceState(activity, outState)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (delegate != null) {
            delegate!!.onActivityDestroyed(activity)
        }
    }
}