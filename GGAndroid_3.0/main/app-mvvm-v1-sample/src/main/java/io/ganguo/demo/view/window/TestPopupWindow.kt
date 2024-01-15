package io.ganguo.demo.view.window

import android.content.Context

import io.ganguo.demo.databinding.WindowTestPopupWindowBinding
import io.ganguo.demo.viewmodel.window.TestPopupWindowVModel
import io.ganguo.viewmodel.core.view.ViewModelWindow

/**
 *
 *
 * PopupWindow - 测试Demo
 *
 * Created by leo on 2018/8/2.
 */

class TestPopupWindow(context: Context) : ViewModelWindow<WindowTestPopupWindowBinding, TestPopupWindowVModel>(context) {


    override fun createViewModel(): TestPopupWindowVModel {
        return TestPopupWindowVModel()
    }

    override fun onViewAttached(viewModel: TestPopupWindowVModel) {
    }

}
