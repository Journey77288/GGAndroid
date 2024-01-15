package io.ganguo.viewmodel.pack.base.viewmodel


import androidx.recyclerview.widget.RecyclerView
import io.ganguo.viewmodel.core.viewinterface.WindowInterface
import io.ganguo.viewmodel.pack.common.popupwindow.REWindowVModel
import io.ganguo.viewmodel.databinding.IncludeRecyclerPopupwindowBinding

/**
 * <pre>
 * author : leo
 * time   : 2018/10/20
 * desc   : 支持垂直方向RecyclerView的PopupWindow
</pre> *
 */
abstract class BaseREVerticalWindowVModel : REWindowVModel<WindowInterface<IncludeRecyclerPopupwindowBinding>>() {
    override fun getOrientation(): Int {
        return RecyclerView.HORIZONTAL
    }

}
