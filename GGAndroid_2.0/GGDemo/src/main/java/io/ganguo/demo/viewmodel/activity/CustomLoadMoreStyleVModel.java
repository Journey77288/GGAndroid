package io.ganguo.demo.viewmodel.activity;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.TimeUnit;

import io.ganguo.demo.R;
import io.ganguo.demo.view.widget.PacemenLoadMoreView;
import io.ganguo.demo.viewmodel.item.ItemDemoVModel;
import io.ganguo.demo.viewmodel.loading.CenterLoadingVModel;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.util.Randoms;
import io.ganguo.viewmodel.common.HFSRecyclerViewModel;
import io.ganguo.viewmodel.common.RecyclerViewModel;
import io.ganguo.viewmodel.common.SimpleViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;
import io.ganguo.viewmodel.callback.ICenterLoadingView;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.vmodel.adapter.ViewModelAdapter;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.ganguo.library.ui.view.ActivityInterface;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 自定义ViewModel Adapter LoadMore Loading
 * Created by leo on 2018/9/14.
 */
public class CustomLoadMoreStyleVModel extends HFSRecyclerViewModel<ActivityInterface<IncludeHfSwipeRecyclerBinding>> {


    @Override
    public void initEmpty(ViewGroup container) {
        super.initEmpty(container);
        ViewModelHelper.bind(container, new SimpleViewModel(R.layout.include_test_empty));
    }

    @Override
    public void onViewAttached(View view) {
        onRefresh();
    }


    @Override
    protected RecyclerViewModel<BaseViewModel, ViewDataBinding> initRecyclerViewModel() {
        RecyclerViewModel viewModel = super.initRecyclerViewModel();
        ViewModelAdapter viewModelAdapter = viewModel.getAdapter();
        viewModelAdapter.setLoadingViewClass(PacemenLoadMoreView.class);
        return viewModel;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getAdapter().clear();
        getAdapter().disableLoadMore();
        loadDelay(2);
    }


    @Override
    public void onLoadMore() {
        super.onLoadMore();
        loadDelay(1);
    }

    @Override
    public void onAdapterNotifyComplete() {
        super.onAdapterNotifyComplete();
        getAdapter().enableLoadMore();
    }

    /**
     * function：load data
     *
     * @param delaySecond
     */
    private void loadDelay(int delaySecond) {
        Observable.just(20)
                .delay(delaySecond, TimeUnit.SECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> toggleEmptyView())
                .compose(RxVMLifecycle.<Integer>bindViewModel(this))
                .subscribe(addIssueItem());
    }

    /**
     * function：add Issue Item
     *
     * @return
     */
    public Consumer<Integer> addIssueItem() {
        return count -> {
            for (int i = 0; i < count; i++) {
                getAdapter().add(createItemDemoVModel());
            }
            getAdapter().notifyDiffUtilSetDataChanged();
            toggleEmptyView();
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
                .setClickAction(s -> ToastHelper.showMessage("click"));
    }

    @Override
    public ICenterLoadingView createLoadingView() {
        return new CenterLoadingVModel();
    }
}
