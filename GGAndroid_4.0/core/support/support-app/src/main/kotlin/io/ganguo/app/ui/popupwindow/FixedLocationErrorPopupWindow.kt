package io.ganguo.app.ui.popupwindow

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.view.View
import android.widget.PopupWindow
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : Fixed a problem with Android N version location display
 * </pre>
 */
open class FixedLocationErrorPopupWindow : PopupWindow {
    constructor(context: Context?) : super(context)

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
     * Fixed a problem with Android N version location display
     *
     * @param anchor
     */
    private fun fixedLocalError(anchor: View) {
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            val rect = Rect()
            anchor.getGlobalVisibleRect(rect)
            var height = ScreenUtils.getScreenHeight() - rect.bottom
            //以下代码可能导致部分机型显示位置不准确
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //    height += AppBars.getStatusBarHeight(anchor.getContext());
            //}
            val navigationBar = (anchor.context as Activity).findViewById<View>(android.R.id.navigationBarBackground)
            //虚拟按键的需要移除虚拟按键的高度
            if (navigationBar != null && navigationBar.visibility == View.VISIBLE) {
                height -= navigationBar.measuredHeight
            }
            setHeight(height)
        }
    }

    /**
     * height is FullScreen
     *
     * @return
     */
    protected open val isHeightMatchParent: Boolean
        get() = true
}
