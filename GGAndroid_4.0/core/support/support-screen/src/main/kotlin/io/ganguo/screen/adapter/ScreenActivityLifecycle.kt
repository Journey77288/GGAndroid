package io.ganguo.screen.adapter

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/23
 *     desc   : ScreenAdapter Activity LifecycleCallback
 * </pre>
 */
internal class ScreenActivityLifecycle(private val callback: (Activity) -> Unit) :
	  Application.ActivityLifecycleCallbacks {
	override fun onActivityPaused(activity: Activity) {
		callback.invoke(activity)
	}

	override fun onActivityStarted(activity: Activity) {
		callback.invoke(activity)
	}

	override fun onActivityDestroyed(activity: Activity) {
	}

	override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
	}

	override fun onActivityStopped(activity: Activity) {
		callback.invoke(activity)
	}

	override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
		callback.invoke(activity)
	}

	override fun onActivityResumed(activity: Activity) {
		callback.invoke(activity)
	}
}