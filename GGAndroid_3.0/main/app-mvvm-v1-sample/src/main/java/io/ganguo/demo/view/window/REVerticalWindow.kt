package io.ganguo.demo.view.window

import android.content.Context

import io.ganguo.demo.viewmodel.window.REVerticalWindowVModel
import io.ganguo.viewmodel.databinding.IncludeRecyclerPopupwindowBinding
import io.ganguo.viewmodel.core.view.ViewModelWindow

/**
 * Vertical 方向 - RecyclerView PopupWindow
 * Created by leo on 2018/10/21.
 */
class REVerticalWindow(context: Context) : ViewModelWindow<IncludeRecyclerPopupwindowBinding, REVerticalWindowVModel>(context) {


    override fun createViewModel(): REVerticalWindowVModel {
        return REVerticalWindowVModel()
    }

    override fun onViewAttached(viewModel: REVerticalWindowVModel) {
    }

}
