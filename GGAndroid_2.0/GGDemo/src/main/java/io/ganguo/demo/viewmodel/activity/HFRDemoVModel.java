package io.ganguo.demo.viewmodel.activity;

import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.TimeUnit;

import io.ganguo.demo.R;
import io.ganguo.demo.viewmodel.item.ItemDemoVModel;
import io.ganguo.demo.viewmodel.item.ItemFooterVModel;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.utils.util.Randoms;
import io.ganguo.viewmodel.common.HFRecyclerViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.common.SimpleViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Roger on 6/23/16.
 */

public class HFRDemoVModel extends HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>> {

    @Override
    public void initHeader(ViewGroup container) {
        super.initHeader(container);
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .appendItemCenter(new HeaderItemViewModel.TitleItemViewModel("HFRecycler Demo"))
                .appendItemLeft(new HeaderItemViewModel.BackItemViewModel(getView().getActivity()))
                .build();
        ViewModelHelper.bind(container, headerViewModel);
    }

    @Override
    public void initFooter(ViewGroup container) {
        super.initFooter(container);
        ViewModelHelper.bind(container, new ItemFooterVModel(onClear()));
    }

    @Override
    public void initEmpty(ViewGroup container) {
        super.initEmpty(container);
        ViewModelHelper.bind(container, new SimpleViewModel(R.layout.include_test_empty));
    }

    @Override
    public void onViewAttached(View view) {
        loadDelay(2);
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

    private void loadDelay(int delaySecond) {
        Observable.just(20)
                .delay(delaySecond, TimeUnit.SECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> toggleEmptyView())
                .compose(RxVMLifecycle.<Integer>bindViewModel(this))
                .subscribe(addIssue());
    }

    public Consumer<Integer> addIssue() {
        return count -> {
            // check attach
            if (!isAttach()) {
                return;
            }
            for (int i = 0; i < count; i++) {
                getAdapter().add(createItemDemoVModel());
            }
            getAdapter().notifyDiffUtilSetDataChanged();
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
}
