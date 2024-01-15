package io.ganguo.demo.view.window;

import android.content.Context;

import io.ganguo.demo.viewmodel.window.REVerticalWindowVModel;
import io.ganguo.viewmodel.databinding.IncludeRecyclerPopupwindowBinding;
import io.ganguo.vmodel.ViewModelPopupWindow;
/**
 * Vertical 方向 - RecyclerView PopupWindow
 * Created by leo on 2018/10/21.
 */
public class REVerticalWindow extends ViewModelPopupWindow<IncludeRecyclerPopupwindowBinding, REVerticalWindowVModel> {
    public REVerticalWindow(Context context) {
        super(context);
    }

    @Override
    public void onViewAttached(REVerticalWindowVModel viewModel) {

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
    public REVerticalWindowVModel createViewModel() {
        return new REVerticalWindowVModel();
    }
}
