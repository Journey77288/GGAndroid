package io.ganguo.core.ui.popupwindow

import android.content.Context
import android.util.AttributeSet
import android.view.View
import io.ganguo.core.ui.dialog.InitResources

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : PopupWindow - 基类
 * </pre>
 */
abstract class BasePopupWindow : FixedLocationErrorPopupWindow, InitResources {
    constructor(context: Context?) : super(context)


    override fun showAsDropDown(anchor: View) {
        beforeInitView()
        initView()
        initListener()
        initData()
        super.showAsDropDown(anchor)
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