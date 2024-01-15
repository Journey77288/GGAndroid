package io.ganguo.demo.viewmodel.page;

import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.PageEmptyDataBinding;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.vmodel.BaseViewModel;

/**
 * 空数据提示页面
 * Created by leo on 2018/11/10.
 */
public class EmptyDataViewModel extends BaseViewModel<ViewInterface<PageEmptyDataBinding>> {
    @Override
    public void onViewAttached(View view) {

    }

    @Override
    public int getItemLayoutId() {
        return R.layout.page_empty_data;
    }
}
