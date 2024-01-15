package io.ganguo.demo.view.dialog;


import android.content.Context;

import io.ganguo.demo.viewmodel.dialog.DialogDemoBottomVModel;
import io.ganguo.viewmodel.databinding.DialogGgBinding;
import io.ganguo.viewmodel.ui.dialog.BottomVModelDialog;

/**
 * <p>
 * 底部弹窗Demo - Dialog
 * </p>
 * Created by leo on 2018/9/30.
 */
public class DemoBottomDialog extends BottomVModelDialog<DialogGgBinding, DialogDemoBottomVModel> {
    public DemoBottomDialog(Context context) {
        super(context);
    }

    @Override
    public DialogDemoBottomVModel createViewModel() {
        return new DialogDemoBottomVModel();
    }

    @Override
    public void onViewAttached(DialogDemoBottomVModel viewModel) {

    }
}
