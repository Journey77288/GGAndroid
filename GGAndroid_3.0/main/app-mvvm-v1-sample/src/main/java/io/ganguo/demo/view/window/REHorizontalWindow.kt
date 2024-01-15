package io.ganguo.demo.view.window

import android.content.Context

import io.ganguo.demo.viewmodel.window.REHorizontalWindowVModel
import io.ganguo.viewmodel.databinding.IncludeRecyclerPopupwindowBinding
import io.ganguo.viewmodel.core.view.ViewModelWindow

/**
 * Horizontal 方向 - RecyclerView PopupWindow
 * Created by leo on 2018/10/21.
 */
class REHorizontalWindow(context: Context) : ViewModelWindow<IncludeRecyclerPopupwindowBinding, REHorizontalWindowVModel>(context) {


    override fun createViewModel(): REHorizontalWindowVModel {
        return REHorizontalWindowVModel()
    }

    override fun onViewAttached(viewModel: REHorizontalWindowVModel) {
    }

    override val context: Context = context
    override val binding: IncludeRecyclerPopupwindowBinding by lazy {
        viewModel.viewInterface.binding
    }
}
