package io.ganguo.demo.viewmodel.activity;

import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.TimeUnit;

import io.ganguo.demo.R;
import io.ganguo.demo.viewmodel.item.ItemDemoVModel;
import io.ganguo.demo.viewmodel.item.ItemFooterVModel;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.rx.RxActions;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.util.Randoms;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.common.HFSRecyclerViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.common.SimpleViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.VMLifecycleHelper;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Roger on 6/22/16.
 */
public class HFSRDemoVModel extends HFSRecyclerViewModel<ActivityInterface<IncludeHfSwipeRecyclerBinding>> {
    @Override
    public void initHeader(ViewGroup container) {
        super.initHeader(container);
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .appendItemCenter(new HeaderItemViewModel.TitleItemViewModel("HFSRecycler Demo"))
                .appendItemLeft(new HeaderItemViewModel.BackItemViewModel(getView().getActivity()))
                .build();
        ViewModelHelper.bind(container, this, headerViewModel);
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

    /**
     * Init data here
     */
    @Override
    public void onViewAttached(View view) {
        loadDelay(2, false);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        loadDelay(2, true);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        loadDelay(0, false);
    }

    /**
     * Clear data
     */
    public Consumer<View> onClear() {
        return view -> {
            getAdapter().onFinishLoadMore(true);
            int size = getAdapter().size();
            getAdapter().clear();
            getAdapter().notifyItemRangeRemoved(0, size);
            toggleEmptyView();
        };
    }

    private void loadDelay(int delaySecond, final boolean isRefresh) {
        Observable.just(15)
                .delay(delaySecond, TimeUnit.SECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxVMLifecycle.<Integer>bindViewModel(this))
                .doOnNext(integer -> {
                    if (isRefresh) {
                        getAdapter().onFinishLoadMore(true);
                        getAdapter().clear();
                        getAdapter().notifyDataSetChanged();
                    }
                })
                .doOnNext(addViewModel())
                .doOnComplete(() -> {
                    if (!isAttach()) {
                        Logger.d("call: " + VMLifecycleHelper.EVENT_VIEW_MODEL_DETACH);
                        return;
                    }
                    getSwipeRefreshLayout().setRefreshing(false);
                    getAdapter().onFinishLoadMore(false);
                    toggleEmptyView();
                })
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable());
    }


    /**
     * function: add ViewModels
     *
     * @return
     */
    public Consumer<Integer> addViewModel() {
        return count -> {
            int j = getAdapter().getItemCount();
            for (int i = 0; i < count; i++) {
                getAdapter().add(createItemDemoVModel());
            }
            getAdapter().notifyItemRangeInserted(j, count);
        };
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
                .setClickAction(s -> ToastHelper.showMessage( "click"));
    }

}
