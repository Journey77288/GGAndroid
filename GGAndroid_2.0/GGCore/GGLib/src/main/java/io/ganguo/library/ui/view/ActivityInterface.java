package io.ganguo.library.ui.view;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

/**
 * Created by Roger on 6/7/16.
 */
public interface ActivityInterface<T extends ViewDataBinding> extends ViewInterface<T> {
    Activity getActivity();

    Bundle getBundle();
}
