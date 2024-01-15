package io.ganguo.viewmodel.common;

import android.view.View;

import io.ganguo.viewmodel.R;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.viewmodel.databinding.IncludeStubViewModelBinding;
import io.ganguo.utils.util.log.Logger;

/**
 * Created by hulkyao on 14/7/2016.
 */

public class StubViewModel extends BaseViewModel<ViewInterface<IncludeStubViewModelBinding>> {
    private int inflateLayoutId;
    private View view;

    public StubViewModel(int inflateLayoutId) {
        this.inflateLayoutId = inflateLayoutId;
    }

    @Override
    public void onViewAttached(View view) {
        getView().getBinding().viewStub.getViewStub().setLayoutResource(getInflateLayoutId());
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.include_stub_view_model;
    }

    public int getInflateLayoutId() {
        return inflateLayoutId;
    }

    public View inflate() {
        view = getView().getBinding().viewStub.getViewStub().inflate();
        return view;
    }

    public <T> T getViewById(int viewId) {
        Logger.d("getViewById:" + "viewId:" + viewId);
        if (view == null) {
            throw new NullPointerException("u need to inflate view stub");
        }
        T t = null;
        try {
            t = (T) view.findViewById(viewId);
        } catch (Exception e) {
            Logger.e("inflate: " + "find view error!");
        }
        return t;
    }
}
