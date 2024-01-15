package io.ganguo.library.ui.bindingadapter.recyclerview;

import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.ganguo.library.ui.adapter.v7.SimpleAdapter;
import io.ganguo.library.ui.bindingadapter.base.BindingViewAdapter;

/**
 * function：RecyclerView DataBinding工具类
 * update by leo on 2018/05/29.
 */
public final class BindingRecyclerViewAdapter extends BindingViewAdapter {
    /**
     * function：xml绑定RecyclerView 相关属性
     *
     * @param recyclerView
     * @param newAdapter
     * @param newLayoutManager
     * @param newRecycleOldViews
     * @param newSpanSizeLookup
     * @param oldAdapter
     * @param oldLayoutManager
     * @param oldSpanSizeLookup
     */
    @BindingAdapter(value = {"android:bind_adapter", "android:bind_layout_manager", "android:bind_span_size_lookup", "android:bind_recycle_OldViews"}, requireAll = false)
    public static void onBindRecyclerView(RecyclerView recyclerView,
                                          SimpleAdapter oldAdapter, RecyclerView.LayoutManager oldLayoutManager,
                                          GridLayoutManager.SpanSizeLookup oldSpanSizeLookup, Boolean oldRecycleOldViews,
                                          SimpleAdapter newAdapter, RecyclerView.LayoutManager newLayoutManager,
                                          GridLayoutManager.SpanSizeLookup newSpanSizeLookup, Boolean newRecycleOldViews) {
        // set layout manager
        if (newLayoutManager != null
                && !newLayoutManager.isAttachedToWindow()
                && newLayoutManager != oldLayoutManager) {
            recyclerView.setLayoutManager(newLayoutManager);
        }
        // set adapter
        if (newAdapter != null && newAdapter != oldAdapter) {
            recyclerView.swapAdapter(newAdapter, newRecycleOldViews != null && newRecycleOldViews);
        }
        // set span size lookup
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null
                && layoutManager instanceof GridLayoutManager
                && newSpanSizeLookup != null
                && newSpanSizeLookup != oldSpanSizeLookup) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(newSpanSizeLookup);
        }
    }

    /**
     * function：xml绑定RecyclerView itemDecoration
     *
     * @param recyclerView
     * @param itemDecoration
     */
    @BindingAdapter(value = "android:bind_item_decoration")
    public static void onBindItemDecoration(RecyclerView recyclerView, RecyclerView.ItemDecoration itemDecoration) {
        if (itemDecoration != null) {
            // 防止重复addDecoration
            recyclerView.removeItemDecoration(itemDecoration);
            recyclerView.addItemDecoration(itemDecoration);
        }
    }


    /**
     * function：xml绑定设置开启RecyclerView滚动水波纹
     *
     * @param recyclerView
     * @param isOverScroll
     */
    @BindingAdapter(value = "android:bind_over_scroll")
    public static void onBindOverScroll(RecyclerView recyclerView, boolean isOverScroll) {
        recyclerView.setOverScrollMode(isOverScroll ? RecyclerView.OVER_SCROLL_ALWAYS : RecyclerView.OVER_SCROLL_NEVER);
    }
}
