package io.ganguo.core.callback.extend

import android.util.Log
import android.view.View


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 单击事件
 * </pre>
 */
abstract class OnSingleClickListener : View.OnClickListener {
    private var lastPressTime: Long = 0
    abstract fun onSingleClick(v: View?)
    override fun onClick(v: View) {
        val pressTime = System.currentTimeMillis()
        if (pressTime - lastPressTime >= DOUBLE_PRESS_INTERVAL) {
            onSingleClick(v)
        } else {
            Log.d("onSingleClick", "double click")
        }
        lastPressTime = pressTime
    }

    companion object {
        /**
         * 点击间隔
         */
        private const val DOUBLE_PRESS_INTERVAL: Long = 600
    }
}