package io.ganguo.demo.view.window;

import android.content.Context;

import io.ganguo.demo.databinding.WindowTestPopupWindowBinding;
import io.ganguo.demo.viewmodel.window.TestPopupWindowVModel;
import io.ganguo.vmodel.ViewModelPopupWindow;

/**
 * <p>
 * PopupWindow - 测试Demo
 * </p>
 * Created by leo on 2018/8/2.
 */

public class TestPopupWindow extends ViewModelPopupWindow<WindowTestPopupWindowBinding, TestPopupWindowVModel> {
    public TestPopupWindow(Context context) {
        super(context);
    }


    @Override
    public TestPopupWindowVModel createViewModel() {
        return new TestPopupWindowVModel();
    }

    @Override
    public void onViewAttached(TestPopupWindowVModel viewModel) {
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
}
