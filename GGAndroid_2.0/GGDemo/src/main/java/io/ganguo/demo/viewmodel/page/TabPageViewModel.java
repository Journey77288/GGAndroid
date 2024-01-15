package io.ganguo.demo.viewmodel.page;

import android.view.View;
import android.view.ViewGroup;

import io.ganguo.demo.R;
import io.ganguo.demo.viewmodel.item.ItemDemoVModel;
import io.ganguo.demo.viewmodel.item.ItemFooterVModel;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.utils.util.Randoms;
import io.ganguo.viewmodel.common.HFSRecyclerViewModel;
import io.ganguo.viewmodel.common.SimpleViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;
import io.reactivex.functions.Consumer;

/**
 * Created by leo on 16/7/11.
 * TabLayout - 对应ViewModel
 */
public class TabPageViewModel extends HFSRecyclerViewModel<ActivityInterface<IncludeHfSwipeRecyclerBinding>> {

    @Override
    public void initHeader(ViewGroup container) {
        super.initHeader(container);
    }

    @Override
    public void initFooter(ViewGroup container) {
        super.initFooter(container);
        ItemFooterVModel testFooterViewModel = new ItemFooterVModel(onClear());
        ViewModelHelper.bind(container, this, testFooterViewModel);
    }

    @Override
    public void initEmpty(ViewGroup container) {
        super.initEmpty(container);
        SimpleViewModel emptyViewModel = new SimpleViewModel(R.layout.include_test_empty);
        ViewModelHelper.bind(container, this, emptyViewModel);
    }

    @Override
    public void onViewAttached(View view) {
        getRecyclerView().setFocusableInTouchMode(false);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getAdapter().disableLoadMore();
        loadDelay(true);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        loadDelay(false);
    }

    /**
     * Clear data
     */
    public Consumer<View> onClear() {
        return view -> {
            getAdapter().clear();
            getAdapter().disableLoadMore();
            getAdapter().notifyDiffUtilSetDataChanged();
            toggleEmptyView();
        };
    }

    private void loadDelay(final boolean isRefresh) {
        if (isRefresh) {
            getAdapter().clear();
        }
        for (Integer i = 0; i < 20; i++) {
            getAdapter().add(createItemDemoVModel());
        }
        getAdapter().notifyDiffUtilSetDataChanged();
        toggleEmptyView();
    }

    /**
     * function: create Item ViewModel
     *
     * @return
     */
    public BaseViewModel createItemDemoVModel() {
        String content = Randoms.getRandomCapitalLetters(12);
        return new ItemDemoVModel<String>()
                .setBtnText("click")
                .setData(content)
                .setContent(content)
                .setClickAction(s -> ToastHelper.showMessage("click"));
    }

    @Override
    public void onAdapterNotifyComplete() {
        super.onAdapterNotifyComplete();
        getAdapter().enableLoadMore();
    }
}
