@file:JvmName("AppBars")

package io.ganguo.utils

import android.R
import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.blankj.utilcode.util.BarUtils

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/20
 *   @desc   :
 * </pre>
 */


private val TAG_STATUS_BAR: String? = "TAG_STATUS_BAR"


/**
 * 设置状态栏颜色
 * @param window Window
 * @param color Int
 * @return View?
 */
fun setStatusBarColor(window: Window,
                      color: Int) {
    return applyStatusBarColor(window, color, false)
}


/**
 * 设置状态栏颜色
 * @param activity Activity
 * @param color Int
 * @return View?
 */
fun setStatusBarColor(activity: Activity,
                      color: Int) {
    applyStatusBarColor(activity.window, color, false)
}


/**
 * 应用状态栏颜色
 * @param activity Activity
 * @param color Int
 * @param isDecor Boolean
 * @return View?
 */
fun applyStatusBarColor(activity: Activity,
                        color: Int,
                        isDecor: Boolean) {
    applyStatusBarColor(activity.window, color, isDecor)
}


/**
 * 应用状态栏颜色
 * @param window Window
 * @param color Int
 * @param isDecor Boolean
 * @return View?
 */
fun applyStatusBarColor(window: Window,
                        color: Int,
                        isDecor: Boolean) {
    window.statusBarColor = color
}

/**
 * 创建 StatusBarView
 * @param context Context
 * @param color Int
 * @return View?
 */
fun createStatusBarView(context: Context,
                        color: Int): View? {
    val statusBarView = View(context)
    statusBarView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, BarUtils.getStatusBarHeight())
    statusBarView.setBackgroundColor(color)
    statusBarView.tag = TAG_STATUS_BAR
    return statusBarView
}
