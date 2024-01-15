package io.ganguo.screen.adapter

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.DisplayMetrics
import io.ganguo.screen.*
import io.ganguo.screen.pattern.AdapterPattern

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/23
 *     desc   : displayMetrics update
 * </pre>
 */
@Suppress("DEPRECATION")
class ScreenMetricsUpdateDelegate {
	private var displayMetrics: DisplayMetrics? = null
	private var scaledDensity: Float = 0f
	private var statusBarHeight: Int = 0
	private var density: Float = 0f

	/**
	 * apply global Screen Configuration
	 * @param application Context
	 * @param config ScreenConfig
	 */
	internal fun applyApplicationScreenConfig(application: Application, configuration: Configuration? = null, config: ScreenConfig) {
		displayMetrics = application.displayMetrics()
		statusBarHeight = application.statusBarHeight()
		scaledDensity = application.scaledDensity()
		density = displayMetrics?.density ?: 0f
		val cf = configuration ?: application.configuration()
		applyScreenConfiguration(application, cf, config)
	}


	/**
	 * apply config to activity Configuration
	 * @param context Context
	 */
	internal fun applyActivityScreenConfig(context: Context, configGlobal: ScreenConfig) {
		val cf = context.configuration()
		applyScreenConfiguration(context, cf, configGlobal)
		val appContext = context.applicationContext
		if (context != appContext) {
			applyScreenConfiguration(appContext, appContext.configuration(), configGlobal)
		}
	}


	/**
	 * apply config to activity singe Configuration
	 * @param context Context
	 */
	internal fun applySingeActivityScreenConfig(context: Context, screenConfig: ScreenConfig) {
		val config = context.resources.configuration
		applyScreenConfiguration(context, config, screenConfig)
	}


	/**
	 * update config to context displayMetrics
	 * @param context Context
	 * @param config ScreenConfig
	 */
	@Suppress("DEPRECATION")
	private fun applyScreenConfiguration(context: Context, configuration: Configuration?, config: ScreenConfig) {
		if (configuration == null) {
			return
		}
		val res = context.resources
		val dm = res.displayMetrics
		var adapterDensity = getBaseDensity(config)
		val adapterDpi = (160 * adapterDensity).toInt()
		val adapterSdy: Float = adapterDensity * (scaledDensity / density)
		val screenWidthDp: Float = context.screenWidth() / adapterDensity

		dm.density = adapterDensity
		dm.scaledDensity = adapterSdy
		dm.densityDpi = adapterDpi
		configuration.densityDpi = adapterDpi
		configuration.screenWidthDp = screenWidthDp.toInt()
		context.resources.updateConfiguration(configuration, dm)
	}


	/**
	 * Calculate the baseline pixel density
	 * @param config ScreenConfig
	 * @return Float
	 */
	private fun getBaseDensity(config: ScreenConfig): Float = let {
		val size: Int? = if (config.adapterPattern == AdapterPattern.DESIGN_HEIGHT) {
			var size = displayMetrics?.heightPixels ?: 0
			size - statusBarHeight
		} else {
			displayMetrics?.widthPixels
		}
		getBaseDensity(size, config.designSize)
	}


	/**
	 * Calculate the baseline pixel density
	 * @param size Float?
	 * @param designSize Float
	 * @return Float
	 */
	private fun getBaseDensity(size: Int?, designSize: Float): Float {
		if (size == null || size < 0) {
			return 0f
		}
		return size.toFloat() / designSize
	}


	/**
	 *  is it versionCode version or above
	 * @param versionCode Int
	 * @return Boolean
	 */
	private fun isAtLeastSdkVersion(versionCode: Int): Boolean {
		return Build.VERSION.SDK_INT >= versionCode
	}
}

