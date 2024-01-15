package io.ganguo.app.ui.popupwindow

import android.content.Context
import android.view.View
import io.ganguo.app.ui.dialog.InitResources

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : Base PopupWindow
 * </pre>
 */
abstract class BasePopupWindow : FixedLocationErrorPopupWindow, InitResources {
    constructor(context: Context?) : super(context)

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int, gravity: Int) {
        onCreate()
        super.showAsDropDown(anchor, xoff, yoff, gravity)
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        onCreate()
        super.showAtLocation(parent, gravity, x, y)
    }

    /**
     * PopupWindow 显示初始化
     */
    protected open fun onCreate() {
        beforeInitView()
        initView()
        initListener()
        initData()
    }


    override fun beforeInitView() {
    }

    override fun initView() {
    }

    override fun initListener() {
    }

    override fun initData() {
    }
}
