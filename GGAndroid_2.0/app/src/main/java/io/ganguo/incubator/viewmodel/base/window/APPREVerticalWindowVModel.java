package io.ganguo.incubator.viewmodel.base.window;

import android.support.v7.widget.RecyclerView;

import io.ganguo.library.ui.view.PopupWindowInterface;
import io.ganguo.viewmodel.common.popupwindow.REWindowVModel;
import io.ganguo.viewmodel.databinding.IncludeRecyclerPopupwindowBinding;

/**
 * <p>
 * 支持垂直方向RecyclerView的PopupWindow
 * </p>
 * Created by leo on 2018/10/20.
 */
public abstract class APPREVerticalWindowVModel extends REWindowVModel<PopupWindowInterface<IncludeRecyclerPopupwindowBinding>> {
    @Override
    public int getOrientation() {
        return RecyclerView.HORIZONTAL;
    }

}
