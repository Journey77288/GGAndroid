package io.ganguo.demo.view.dialog;

import android.content.Context;

import io.ganguo.demo.viewmodel.dialog.DialogDemoBtmRecyclerVModel;
import io.ganguo.viewmodel.databinding.DialogGgBinding;
import io.ganguo.viewmodel.ui.dialog.BottomVModelDialog;

/**
 * <p>
 * 支持RecyclerView的底部弹出Demo
 * </p>
 * Created by leo on 2018/9/30.
 */
public class DemoBottomRecyclerDialog extends BottomVModelDialog<DialogGgBinding, DialogDemoBtmRecyclerVModel> {
    public DemoBottomRecyclerDialog(Context context) {
        super(context);
    }

    @Override
    public DialogDemoBtmRecyclerVModel createViewModel() {
        return new DialogDemoBtmRecyclerVModel();
    }

    @Override
    public void onViewAttached(DialogDemoBtmRecyclerVModel viewModel) {

    }
}
