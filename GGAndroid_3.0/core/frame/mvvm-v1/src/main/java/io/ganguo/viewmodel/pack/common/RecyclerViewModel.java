package io.ganguo.viewmodel.pack.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.ganguo.appcompat.widget.GridWrapperLayoutManager;
import io.ganguo.appcompat.widget.LinearWrapperLayoutManager;
import io.ganguo.appcompat.widget.StaggeredGridWrapperLayoutManager;
import io.ganguo.image.core.ImageHelper;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.core.BaseViewModel;
import io.ganguo.viewmodel.core.adapter.ViewModelAdapter;
import io.ganguo.viewmodel.core.adapter.diffuitl.ViewModelDiffUtil;
import io.ganguo.viewmodel.core.viewinterface.ViewInterface;
import io.ganguo.viewmodel.databinding.IncludeRecyclerBinding;

/**
 * RecyclerViewModel
 * <p>
 * 用法:
 * 1、绑定include layout
 * 创建 RecyclerViewModel 实例与 layout 中的 R.layout.include_recycler 绑定
 * recyclerViewModel = RecyclerViewModel.linerLayout(getContext(), LinearLayoutManager.VERTICAL);
 * ViewModelHelper.bind(getViewInterface().getBinding().includeRecycler, recyclerViewModel);
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
 * Dialog: see example{@link io.ganguo.core.ui.dialog}
 * <p>
 * Created by Roger on 3/29/16.
 */
public class RecyclerViewModel<T extends ViewDataBinding, B extends ViewInterface<T>> extends BaseViewModel<B> {
    private Context context;

    private ViewModelAdapter mAdapter;
    @Dimension
    private int viewHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    @Dimension
    private int viewWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    private RecyclerView.ItemDecoration itemDecoration;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager.SpanSizeLookup spanSizeLookup;
    private RecyclerView.ItemAnimator itemAnimator;

    private int itemLayoutId = -1;
    private int paddingLeft = 0;
    private int paddingRight = 0;
    private int paddingTop = 0;
    private int paddingBottom = 0;
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
        ImageHelper.bindImageLoadScrollIdle(getRecyclerView());
    }


    @Override
    public int getLayoutId() {
        return itemLayoutId == -1 ? R.layout.include_recycler : itemLayoutId;
    }

    public static <T extends ViewDataBinding, B extends ViewInterface<T>> RecyclerViewModel<T, B> linerLayout(Context context, int orientation) {
        return new RecyclerViewModel<T, B>(context)
                .layoutManager(new LinearWrapperLayoutManager(context, orientation, false));
    }

    public static<T extends ViewDataBinding, B extends ViewInterface<T>> RecyclerViewModel<T, B> gridLayout(Context context, int spanCount, int orientation) {
        return new RecyclerViewModel<T, B>(context)
                .layoutManager(new GridWrapperLayoutManager(context, spanCount, orientation, false));
    }

    public static <T extends ViewDataBinding, B extends ViewInterface<T>> RecyclerViewModel<T, B> staggeredGridLayout(Context context, int spanCount, int orientation) {
        return new RecyclerViewModel<T, B>(context)
                .layoutManager(new StaggeredGridWrapperLayoutManager(spanCount, orientation));
    }

    public RecyclerViewModel<T, B> padding(int padding) {
        this.paddingLeft = padding;
        this.paddingRight = padding;
        this.paddingTop = padding;
        this.paddingBottom = padding;
        notifyChange();
        return this;
    }

    public RecyclerViewModel<T, B> padding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
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

    @Override
    public Context getContext() {
        return context;
    }

    public ViewModelAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ViewModelAdapter(getContext(), this, new ViewModelDiffUtil());
        }
        return mAdapter;
    }


    public void setRecyclerAdapter(ViewModelAdapter adapter) {
        this.mAdapter = adapter;
        notifyChange();
    }

    @Override
    public void onDestroy() {
        context = null;
        super.onDestroy();
    }

    /**
     * set RecyclerView height
     */
    public RecyclerViewModel<T, B> setViewHeight(int viewHeight) {
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

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }
}
