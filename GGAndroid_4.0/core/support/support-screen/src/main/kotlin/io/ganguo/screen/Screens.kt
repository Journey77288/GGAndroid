@file:JvmName("Screens")
@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/23
 *     desc   : Screen Utils
 * </pre>
 */
package io.ganguo.screen

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager


/**
 * Context is Null
 * @receiver Context?
 * @return Boolean
 */
fun Context?.isNull(): Boolean {
    return this == null
}

/**
 * Context is Not Null
 * @receiver Context
 * @return Boolean
 */
fun Context?.isNotNull(): Boolean {
    return this != null
}

/**
 * get DisplayMetrics from Context
 * @receiver Context?
 * @return DisplayMetrics?
 */
fun Context?.displayMetrics(): DisplayMetrics? {
    return this!!.resources?.displayMetrics
}


/**
 * get Display from Context
 * @receiver Context?
 * @return Display
 */
fun Context?.display(): Display? {
    return this?.windowService()?.defaultDisplay
}


/**
 * get WindowManager from Context
 * @receiver Context?
 * @return WindowManager
 */
fun Context?.windowService(): WindowManager? {
    return this?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
}

/**
 * get font size scaledDensity from Context
 * @receiver Context?
 * @return Float?
 */
fun Context?.scaledDensity(): Float {
    return displayMetrics()?.scaledDensity ?: 0f
}

/**
 * get screen width from Context
 * @receiver Context?
 * @return Int?
 */
fun Context?.screenWidth(): Int {
    return displayMetrics()?.widthPixels ?: 0
}

/**
 * get screen width from Context
 * @receiver Context?
 * @return Int?
 */
fun Context?.screenHeight(): Int {
    return displayMetrics()?.heightPixels ?: 0
}

/**
 * get Configuration from Context
 * @receiver Context?
 * @return Configuration?
 */
fun Context?.configuration(): Configuration? {
    return this?.resources?.configuration
}

/**
 * current Screen is landscape from Context
 * @receiver Context?
 * @return Boolean
 */
fun Context?.isLandscape(): Boolean {
    val configuration = configuration() ?: return false
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

/**
 * current Screen is portrait from Context
 * @receiver Context?
 * @return Boolean
 */
fun Context?.isPortrait(): Boolean {
    return !isLandscape()
}


/**
 * get statusBar Height  from Context
 * @receiver Context?
 * @return Int
 */
fun Context?.statusBarHeight(): Int {
    val default = 38
    if (isNull()) {
        return default
    }
    var height = default
    try {
        val c = Class.forName("com.android.internal.R\$dimen")
        val obj = c.getDeclaredConstructor().newInstance()
        val field = c.getField("status_bar_height")
        val x: Int = field[obj].toString().toInt()
        height = this?.resources?.getDimensionPixelSize(x) ?: default
    } catch (e1: Exception) {
        e1.printStackTrace()
    }
    return height
}


/**
 *  is it versionCode version or above
 * @param versionCode Int
 * @return Boolean
 */
fun isAtLeastSdkVersion(versionCode: Int): Boolean {
    return Build.VERSION.SDK_INT >= versionCode
}

