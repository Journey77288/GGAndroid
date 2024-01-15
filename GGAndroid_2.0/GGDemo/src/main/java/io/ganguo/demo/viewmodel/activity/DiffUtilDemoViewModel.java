package io.ganguo.demo.viewmodel.activity;

import android.view.View;
import android.view.ViewGroup;


import io.ganguo.demo.R;
import io.ganguo.demo.entity.CommonDemoEntity;
import io.ganguo.demo.viewmodel.item.ItemDiffUtilDemoVModel;
import io.ganguo.utils.util.Tasks;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.common.HFSRecyclerViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.common.item.ItemSampleVModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;

/**
 * <p>
 * DiffUtil 刷新数据工具类 Demo
 * </p>
 * Created by leo on 2018/8/27.
 */
public class DiffUtilDemoViewModel extends HFSRecyclerViewModel<ActivityInterface<IncludeHfSwipeRecyclerBinding>> {
    private String[] text = {
            "Java", "C++", "GoLang", "PHP", "C", "PWA", "Vue", "Python", "Flutter", "Kotlin", "AA", "BB", "CC", "DD", "EE", "FF"};

    @Override
    public void onViewAttached(View view) {
        init();
    }


    @Override
    public void onLoadMore() {
        super.onLoadMore();
        Logger.e("DiffUtilDemoViewModel：onLoadMore");
        init();
    }

    @Override
    public void initHeader(ViewGroup container) {
        super.initHeader(container);
        setEnableHeaderElevation(false);
        ViewModelHelper.bind(container, this, onCreateHeaderVModel());
        ViewModelHelper.bind(container, this, onCreateRefreshVModel());
    }


    @Override
    public void initFooter(ViewGroup container) {
        super.initFooter(container);
        setEnableFooterElevation(false);
        ViewModelHelper.bind(container, this, onCreateLoadMoreVModel());
    }

    /**
     * function：create header ViewModel
     *
     * @return
     */
    protected BaseViewModel onCreateHeaderVModel() {
        return new HeaderViewModel.Builder()
                .appendItemCenter(new HeaderItemViewModel
                        .TitleItemViewModel(getClass().getSimpleName()))
                .appendItemLeft(new HeaderItemViewModel
                        .BackItemViewModel(getView().getActivity()))
                .build();
    }


    /**
     * function：init data
     */
    protected void init() {
        for (int i = 0; i < text.length; i++) {
            getAdapter().add(onCreateDiffItemVModel(text[i]));
        }
        getAdapter().notifyDiffUtilSetDataChanged();
        toggleEmptyView();
    }


    /**
     * function：create Diff item viewModel
     *
     * @param text
     * @return
     */
    protected BaseViewModel onCreateDiffItemVModel(String text) {
        return new ItemDiffUtilDemoVModel(new CommonDemoEntity()
                .setText(text))
                .setRemoveConsumer(vModel -> {
                    getAdapter().remove(vModel);
                    getAdapter().notifyDiffUtilSetDataChanged();
                });
    }


    /**
     * function：create Refresh ViewModel
     *
     * @return
     */
    protected BaseViewModel onCreateRefreshVModel() {
        return new ItemSampleVModel()
                .setAction(() -> {
                    getSwipeRefreshLayout().setRefreshing(true);
                    getSwipeRefreshLayout().postDelayed(() -> {
                        onRefresh();
                    }, 500);
                })
                .setText("模拟下拉刷新")
                .setBg(R.color.red);
    }


    /**
     * function：create LoadMore ViewModel
     *
     * @return
     */
    protected BaseViewModel onCreateLoadMoreVModel() {
        return new ItemSampleVModel()
                .setAction(() -> {
                    init();
                })
                .setText("模拟上拉加载");
    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        getAdapter().clear();
        init();
    }

    @Override
    public void onAdapterNotifyComplete() {
        super.onAdapterNotifyComplete();
        getAdapter().enableLoadMore();
    }
}
