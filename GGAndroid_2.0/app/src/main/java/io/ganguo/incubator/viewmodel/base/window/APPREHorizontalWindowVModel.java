package io.ganguo.incubator.viewmodel.base.window;


import android.support.v7.widget.RecyclerView;

import io.ganguo.library.ui.view.PopupWindowInterface;
import io.ganguo.viewmodel.common.popupwindow.REWindowVModel;
import io.ganguo.viewmodel.databinding.IncludeRecyclerPopupwindowBinding;

/**
 * <p>
 * 支持水平方向RecyclerView的PopupWindow
 * </p>
 * Created by leo on 2018/10/20.
 */
public abstract   class APPREHorizontalWindowVModel extends REWindowVModel<PopupWindowInterface<IncludeRecyclerPopupwindowBinding>> {

    @Override
    public int getOrientation() {
        return RecyclerView.HORIZONTAL;
    }

}
