package io.ganguo.picker.util

import android.content.Context
import android.content.pm.PackageManager

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/07/17
 *     desc   : 媒体工具类
 * </pre>
 */
object MediaUtils {
    /**
     * 判断设备是否有相机
     *
     * @param context
     * @return
     */
    fun hasCamera(context: Context): Boolean {
        val packageManager = context.applicationContext.packageManager
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }
}