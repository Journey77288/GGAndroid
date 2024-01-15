package io.ganguo.viewmodel.common;

import android.support.annotation.LayoutRes;
import android.view.View;

import io.ganguo.vmodel.BaseViewModel;


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
    public int getItemLayoutId() {
        return layoutId;
    }
}
