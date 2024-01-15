package io.ganguo.library.ui.view;

import android.app.Dialog;

import android.databinding.ViewDataBinding;

/**
 * Created by Roger on 6/20/16.
 */

public interface DialogInterface<T extends ViewDataBinding> extends ViewInterface<T> {
    Dialog getDialog();
}
