package io.ganguo.core.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import io.ganguo.core.R

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : Dialog - 基类
 * </pre>
 */
abstract class BaseDialog @JvmOverloads constructor(context: Context, theme: Int = R.style.Dialog_Immersion) : Dialog(context, theme), InitResources {
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        // init resources
        beforeInitView()
        initView()
        initListener()
        initData()
        initAnimation()
        initDialog()
    }


    /**
     * Dialog init
     *
     * @return
     */
    protected open fun initDialog() {
        window!!.setLayout(dialogWidth, dialogHeight)
    }

    /**
     * 弹窗宽度
     *
     * @return
     */
    protected abstract val dialogWidth: Int

    /**
     * 弹窗高度
     *
     * @return
     */
    protected abstract val dialogHeight: Int

    /**
     * 设置弹窗动画
     */
    protected open fun initAnimation() {
        val params = window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.windowAnimations = R.style.Dialog_Animation
        params.gravity = Gravity.BOTTOM
        window!!.attributes = params
    }
}