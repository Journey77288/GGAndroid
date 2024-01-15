package io.ganguo.screen.adapter

import  android.app.Activity
import android.app.Application
import io.ganguo.screen.port.IScreenConfigNeglect
import io.ganguo.screen.port.IScreenSingeConfig


/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/23
 *     desc   : ScreenAdapter Util
 * </pre>
 */
class ScreenAdapter private constructor(
	  private val configGlobal: ScreenConfig,
	  private var delegate: ScreenMetricsUpdateDelegate) {

	/**
	 * Initialize in Application
	 * @param application Application
	 */
	internal fun initialize(application: Application) {
		application.registerActivityLifecycleCallbacks(ScreenActivityLifecycle {
			applyActivityScreenConfig(it)
		})
		application.registerComponentCallbacks(ScreenAppCallback {
			delegate.applyApplicationScreenConfig(application, it, configGlobal)
		})
		delegate.applyApplicationScreenConfig(application, config = configGlobal)
	}


	/**
	 * apply Activity config
	 * @param activity Activity
     * Notice: 当部分系统上，进入不用方向的Activity，如果遇到适配方案失效，可主动调取该方法来恢复适配主动
	 */
    fun applyActivityScreenConfig(activity: Activity) {
		if (activity is IScreenConfigNeglect) {
			return
		}
		if (activity is IScreenSingeConfig) {
			delegate.applySingeActivityScreenConfig(activity, activity.screenConfig)
		} else {
			delegate.applyActivityScreenConfig(activity,configGlobal)
		}
	}


	companion object {
		private lateinit var instance: ScreenAdapter

		/**
		 * Returns the global instance of [ScreenAdapter] created via init method.
		 *
		 * @throws IllegalStateException if it was not initialized properly.
		 */
		@JvmStatic
		private fun get(): ScreenAdapter {
			check(Companion::instance.isInitialized) { "Lingver should be initialized first" }
			return instance
		}

		/**
		 * Creates and sets up the global instance using a provided screen and the default config.
		 * @param application Application
		 * @param builder ScreenConfig.Builder
		 * @return ScreenAdapter
		 */
		fun initialize(application: Application, builder: ScreenConfig.Builder): ScreenAdapter {
			return initialize(application, builder.build())
		}

		/**
		 * Creates and sets up the global instance using a provided screen and the default config.
		 * @param application Application
		 * @param defaultConfig ScreenConfig
		 * @return ScreenAdapter
		 */
		private fun initialize(
			  application: Application,
			  defaultConfig: ScreenConfig
		): ScreenAdapter {
			check(!Companion::instance.isInitialized) { "Already initialized" }
			val adapter = ScreenAdapter(defaultConfig, ScreenMetricsUpdateDelegate())
			adapter.initialize(application)
			instance = adapter
			return adapter
		}
	}
}