package io.ganguo.viewmodel.pack.common;

import android.view.View;

import androidx.annotation.LayoutRes;
import io.ganguo.viewmodel.core.BaseViewModel;


/**
 * 直接展示Layout
 *
 * Created by Roger on 6/4/16.
 */
public class SimpleViewModel extends BaseViewModel {
    @LayoutRes
    private int layoutId;

    public SimpleViewModel(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public void onViewAttached(View view) {

    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }
}
