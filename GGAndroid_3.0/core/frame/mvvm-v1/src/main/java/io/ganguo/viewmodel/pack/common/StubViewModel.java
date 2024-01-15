package io.ganguo.viewmodel.pack.common;

import android.view.View;

import io.ganguo.viewmodel.core.viewinterface.ViewInterface;
import io.ganguo.log.core.Logger;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.databinding.IncludeStubViewModelBinding;
import io.ganguo.viewmodel.core.BaseViewModel;

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
        getViewInterface().getBinding().viewStub.getViewStub().setLayoutResource(getInflateLayoutId());
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_stub_view_model;
    }

    public int getInflateLayoutId() {
        return inflateLayoutId;
    }

    public View inflate() {
        view = getViewInterface().getBinding().viewStub.getViewStub().inflate();
        return view;
    }

    public <T> T getViewInterfaceById(int viewId) {
        Logger.INSTANCE.d("getViewInterfaceById:" + "viewId:" + viewId);
        if (view == null) {
            throw new NullPointerException("u need to inflate view stub");
        }
        T t = null;
        try {
            t = (T) view.findViewById(viewId);
        } catch (Exception e) {
            Logger.INSTANCE.e("inflate: " + "find view error!");
        }
        return t;
    }
}
