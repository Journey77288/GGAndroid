package io.ganguo.viewmodel.common;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import io.ganguo.library.core.image.PhotoPicassoLoader;
import io.ganguo.library.ui.widget.layoutmanager.GridLayoutManagerWrapper;
import io.ganguo.library.ui.widget.layoutmanager.LinearLayoutManagerWrapper;
import io.ganguo.library.ui.widget.layoutmanager.StaggeredGridLayoutManagerWrapper;
import io.ganguo.viewmodel.R;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.adapter.ViewModelAdapter;

/**
 * RecyclerViewModel
 * <p>
 * 用法:
 * 1、绑定include layout
 * 创建 RecyclerViewModel 实例与 layout 中的 R.layout.include_recycler 绑定
 * recyclerViewModel = RecyclerViewModel.linerLayout(getContext(), LinearLayoutManager.VERTICAL);
 * ViewModelHelper.bind(getView().getBinding().includeRecycler, recyclerViewModel);
 * <p>
 * 2、直接与Activity、Fragment、Dialog、View绑定,设置为根布局
 * recyclerViewModel = initRecyclerViewModel();
 * ViewModelHelper.bind(this, recyclerViewModel);
 * Activity:
 * DataBindingUtil.setContentView(this, recyclerViewModel.getItemLayoutId());
 * Fragment:
 * public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 * return recyclerViewModel.getRootView();
 * }
 * Dialog: see example{@link io.ganguo.library.ui.dialog}
 * <p>
 * Created by Roger on 3/29/16.
 */
public class RecyclerViewModel<T extends BaseViewModel, B extends ViewDataBinding> extends BaseViewModel {
    private Context context;

    private ViewModelAdapter<B> mAdapter;
    @Dimension
    private int viewHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    @Dimension
    private int viewWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    private RecyclerView.ItemDecoration itemDecoration;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager.SpanSizeLookup spanSizeLookup;
    private RecyclerView.ItemAnimator itemAnimator;

    private int itemLayoutId = -1;
    private int padding = 0;
    private boolean isOverScroll = true;
    private ObservableBoolean clickable = new ObservableBoolean(true);

    public ObservableBoolean getClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = new ObservableBoolean(clickable);
    }

    public RecyclerViewModel(Context context) {
        this(context, -1);
    }

    public RecyclerViewModel(Context context, int itemLayoutId) {
        this.context = context;
        this.itemLayoutId = itemLayoutId;
    }


    @Override
    public void onAttach() {
        super.onAttach();
    }

    @Override
    public void onViewAttached(View view) {
    }


    @Override
    public int getItemLayoutId() {
        return itemLayoutId == -1 ? R.layout.include_recycler : itemLayoutId;
    }

    public static <T extends BaseViewModel, B extends ViewDataBinding> RecyclerViewModel<T, B> linerLayout(Context context, int orientation) {
        return new RecyclerViewModel<T, B>(context)
                .layoutManager(new LinearLayoutManagerWrapper(context, orientation, false));
    }

    public static <T extends BaseViewModel, B extends ViewDataBinding> RecyclerViewModel<T, B> gridLayout(Context context, int spanCount, int orientation) {
        return new RecyclerViewModel<T, B>(context)
                .layoutManager(new GridLayoutManagerWrapper(context, spanCount, orientation, false));
    }

    public static <T extends BaseViewModel, B extends ViewDataBinding> RecyclerViewModel<T, B> staggeredGridLayout(Context context, int spanCount, int orientation) {
        return new RecyclerViewModel<T, B>(context)
                .layoutManager(new StaggeredGridLayoutManagerWrapper(spanCount, orientation));
    }

    public RecyclerViewModel<T, B> padding(int padding) {
        this.padding = padding;
        notifyChange();
        return this;
    }

    public RecyclerViewModel<T, B> layoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        notifyChange();
        return this;
    }

    public RecyclerViewModel<T, B> itemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        this.itemAnimator = itemAnimator;
        notifyChange();
        return this;
    }

    public RecyclerViewModel<T, B> onViewAttach(OnViewAttachListener onViewAttachListener) {
        setOnViewAttachListener(onViewAttachListener);
        return this;
    }

    /**
     * 是否开启水波纹
     *
     * @param isOverScroll
     * @return
     */
    public RecyclerViewModel<T, B> isOverScroll(boolean isOverScroll) {
        this.isOverScroll = isOverScroll;
        notifyChange();
        return this;
    }

    public RecyclerViewModel<T, B> itemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.itemDecoration = itemDecoration;
        notifyChange();
        return this;
    }

    public RecyclerViewModel<T, B> spanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        this.spanSizeLookup = spanSizeLookup;
        notifyChange();
        return this;
    }

    public Context getContext() {
        return context;
    }

    public ViewModelAdapter<B> getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ViewModelAdapter<>(getContext(), this);
            mAdapter.disableLoadMore();
        }
        return mAdapter;
    }


    public void setRecyclerAdapter(ViewModelAdapter<B> adapter) {
        this.mAdapter = adapter;
        notifyChange();
    }

    @Override
    public void onDestroy() {
        context = null;
        if (mAdapter != null) {
            mAdapter.destroy();
        }
        super.onDestroy();
    }

    /**
     * set RecyclerView height
     */
    public RecyclerViewModel setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
        return this;
    }

    /**
     * get RecyclerView height
     */
    public int getViewHeight() {
        return viewHeight;
    }


    /**
     * set RecyclerView viewWidth
     *
     * @param viewWidth
     * @return
     */
    public RecyclerViewModel<T, B> setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
        return this;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    /**
     * Get RecyclerView after bind to ViewInterface
     */
    @NonNull
    public RecyclerView getRecyclerView() {
        return (RecyclerView) getRootView();
    }

    public boolean isOverScroll() {
        return isOverScroll;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return itemDecoration;
    }

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return spanSizeLookup;
    }

    public RecyclerView.ItemAnimator getItemAnimator() {
        return itemAnimator;
    }


    public int getPadding() {
        return padding;
    }
}
