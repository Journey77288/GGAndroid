package io.ganguo.demo.viewmodel.window

import android.view.View
import io.ganguo.viewmodel.core.viewinterface.WindowInterface
import io.ganguo.demo.R
import io.ganguo.demo.databinding.WindowTestPopupWindowBinding
import io.ganguo.viewmodel.core.BaseViewModel

/**
 *
 *
 * PopupWindow - 测试Demo
 *
 * Created by leo on 2018/8/2.
 */

class TestPopupWindowVModel : BaseViewModel<WindowInterface<WindowTestPopupWindowBinding>>() {

    override fun onViewAttached(view: View) {

    }

    override val layoutId: Int by lazy {
        R.layout.window_test_popup_window
    }
}
