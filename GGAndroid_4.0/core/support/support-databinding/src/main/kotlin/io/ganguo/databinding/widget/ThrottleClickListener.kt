package io.ganguo.databinding.widget

import android.view.View

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/04/16
 *     desc   : 防抖点击监听
 * </pre>
 * @property [threshold] 点击间距时长(单位：毫秒)
 * @property [listener] 点击回调监听
 */
class ThrottleClickListener(
    private val threshold: Long = 1000L,
    private val listener: View.OnClickListener?
) : View.OnClickListener {
    private var lastClickTime = 0L
    private var lastViewId = 0

    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        val isNotDoubleClick = currentTime - lastClickTime > threshold
        val isNotSameView = v?.id != lastViewId
        if (isNotDoubleClick || isNotSameView) {
            lastClickTime = currentTime
            listener?.onClick(v)
        }
        lastViewId = v?.id ?: 0
    }
}