package io.ganguo.app.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : Base Dialog
 * </pre>
 */
abstract class BaseDialog constructor(context: Context, theme: Int) : Dialog(context, theme), InitResources {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeInitView()
        initView()
        initListener()
        initData()
        initWindow()
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun beforeInitView() {
    }


    /**
     * Dialog Window init
     *
     * @return
     */
    protected open fun initWindow() {
        window?.let {
            it.attributes.width = windowWidth
            it.attributes.height = windowHeight
            it.attributes.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        }
    }

    /**
     * dialog window width
     *
     * @return
     */
    protected abstract val windowWidth: Int

    /**
     * dialog window height
     *
     * @return
     */
    protected abstract val windowHeight: Int

}
