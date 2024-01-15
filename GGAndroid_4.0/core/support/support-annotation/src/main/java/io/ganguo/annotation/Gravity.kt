package io.ganguo.annotation

import android.view.Gravity
import androidx.annotation.IntDef

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/29
 *   @desc   : 位置属性注解
 * </pre>
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(Gravity.START, Gravity.END, Gravity.BOTTOM, Gravity.TOP, Gravity.CENTER, Gravity.LEFT, Gravity.RIGHT, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL)
annotation class Gravity
