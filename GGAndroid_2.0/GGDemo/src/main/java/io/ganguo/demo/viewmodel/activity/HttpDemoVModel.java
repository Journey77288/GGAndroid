package io.ganguo.demo.viewmodel.activity;

import android.view.View;

import java.util.concurrent.TimeUnit;

import io.ganguo.demo.module.impl.APIOneImpl;
import io.ganguo.demo.viewmodel.item.ItemOneListVModel;
import io.ganguo.demo.viewmodel.loading.OnListLoadingVModel;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.rx.RxActions;
import io.ganguo.rx.RxCollections;
import io.ganguo.viewmodel.callback.ICenterLoadingView;
import io.ganguo.viewmodel.common.HFSRecyclerViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>
 * 网络请求Demo
 * </p>
 * Created by leo on 2018/7/30.
 */
public class HttpDemoVModel extends HFSRecyclerViewModel<ActivityInterface<IncludeHfSwipeRecyclerBinding>> {

    @Override
    public void onViewAttached(View view) {
        getOneList();

    }


    /**
     * function：获取一个列表数据
     */
    protected void getOneList() {
        APIOneImpl
                .get()
                .getOneList("3528")
                .subscribeOn(Schedulers.io())
                .delay(3000, TimeUnit.MILLISECONDS)
                .map(entity -> entity.getContentOneEntities())
                .compose(RxCollections.filterNotEmpty())
                .compose(RxCollections.emitItems())
                .map(e -> new ItemOneListVModel(e))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .map(itemOneListVModels -> itemOneListVModels)
                .doOnSuccess(itemOneListVModels -> {
                    getAdapter().clear();
                    getAdapter().addAll(itemOneListVModels);
                    getAdapter().notifyDiffUtilSetDataChanged();
                })
                .doFinally(() -> toggleEmptyView())
                .compose(RxVMLifecycle.bindSingleViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(getClass().getSimpleName() + "--getOneList--"));
    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        getOneList();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        getOneList();
    }


    @Override
    public ICenterLoadingView createLoadingView() {
        return new OnListLoadingVModel();
    }
}
