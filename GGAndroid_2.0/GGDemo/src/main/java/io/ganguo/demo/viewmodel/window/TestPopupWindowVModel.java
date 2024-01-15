package io.ganguo.demo.viewmodel.window;

import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.WindowTestPopupWindowBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.PopupWindowInterface;

/**
 * <p>
 * PopupWindow - 测试Demo
 * </p>
 * Created by leo on 2018/8/2.
 */

public class TestPopupWindowVModel extends BaseViewModel<PopupWindowInterface<WindowTestPopupWindowBinding>> {
    @Override
    public int getItemLayoutId() {
        return R.layout.window_test_popup_window;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
