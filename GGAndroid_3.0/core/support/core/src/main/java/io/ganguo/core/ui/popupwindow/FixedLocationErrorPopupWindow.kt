package io.ganguo.core.ui.popupwindow

import android.content.Context
import android.graphics.Rect
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.AttributeSet
import android.view.View
import android.widget.PopupWindow

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 修复Android 7.0以上位置显示不正确的PopupWindow
 * </pre>
 */
open class FixedLocationErrorPopupWindow : PopupWindow {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
    constructor() : super()
    constructor(contentView: View?) : super(contentView)
    constructor(width: Int, height: Int) : super(width, height)
    constructor(contentView: View?, width: Int, height: Int) : super(contentView, width, height)
    constructor(contentView: View?, width: Int, height: Int, focusable: Boolean) : super(contentView, width, height, focusable)

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int, gravity: Int) {
        if (isHeightMatchParent) {
            fixedLocalError(anchor)
        }
        super.showAsDropDown(anchor, xoff, yoff, gravity)
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        if (isHeightMatchParent) {
            fixedLocalError(parent)
        }
        super.showAtLocation(parent, gravity, x, y)
    }

    /**
     * 修复Android 7.0以上位置显示不正确的PopupWindow
     *
     * @param anchor
     */
    private fun fixedLocalError(anchor: View) {
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            val rect = Rect()
            anchor.getGlobalVisibleRect(rect)
            val height = anchor.resources.displayMetrics.heightPixels - rect.bottom
            //以下代码可能导致部分机型显示位置不准确
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //    height += AppBars.getStatusBarHeight(anchor.getContext());
            //}
            setHeight(height)
        }
    }

    /**
     * PopupWindow 高度是否铺满全屏
     *
     * @return
     */
    val isHeightMatchParent: Boolean
        get() = true
}