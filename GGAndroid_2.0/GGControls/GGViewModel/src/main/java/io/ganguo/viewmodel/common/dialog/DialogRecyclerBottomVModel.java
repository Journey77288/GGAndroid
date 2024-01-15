package io.ganguo.viewmodel.common.dialog;

import android.view.Gravity;

import io.ganguo.viewmodel.callback.IDialogViewInterface;
import io.ganguo.viewmodel.databinding.DialogGgBinding;

/**
 * <p>
 * RecyclerViewModel Bottom Dialog
 * </p>
 * Created by leo on 2018/9/18.
 */
public abstract class DialogRecyclerBottomVModel extends DialogRecyclerVModel<DialogGgBinding> implements IDialogViewInterface {
    @Override
    public int getLayoutGravity() {
        return Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    }
}
