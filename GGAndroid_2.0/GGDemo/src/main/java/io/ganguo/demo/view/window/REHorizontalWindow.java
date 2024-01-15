package io.ganguo.demo.view.window;

import android.content.Context;

import io.ganguo.demo.viewmodel.window.REHorizontalWindowVModel;
import io.ganguo.viewmodel.databinding.IncludeRecyclerPopupwindowBinding;
import io.ganguo.vmodel.ViewModelPopupWindow;

/**
 * Horizontal 方向 - RecyclerView PopupWindow
 * Created by leo on 2018/10/21.
 */
public class REHorizontalWindow extends ViewModelPopupWindow<IncludeRecyclerPopupwindowBinding, REHorizontalWindowVModel> {
    public REHorizontalWindow(Context context) {
        super(context);
    }

    @Override
    public void onViewAttached(REHorizontalWindowVModel viewModel) {

    }

    @Override
    public boolean isWindowWidthFull() {
        return false;
    }

    @Override
    public boolean isWindowHeightFull() {
        return false;
    }

    @Override
    public boolean isTouchOutsideDismiss() {
        return true;
    }

    @Override
    public REHorizontalWindowVModel createViewModel() {
        return new REHorizontalWindowVModel();
    }
}
