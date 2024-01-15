package io.ganguo.core.ui.bindingadapter.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ganguo.core.ui.bindingadapter.base.BindingViewAdapter

/**
 * <pre>
 * author : leo
 * time   : 2018/05/29
 * desc   : RecyclerView DataBinding工具类
</pre> *
 */
object BindingRecyclerViewAdapter : BindingViewAdapter() {
    /**
     * xml绑定RecyclerView 相关属性
     *
     * @param recyclerView
     * @param adapter
     * @param layoutManager
     * @param spanSizeLookup
     */
    @kotlin.jvm.JvmStatic
    @BindingAdapter(value = ["android:bind_adapter", "android:bind_layout_manager", "android:bind_span_size_lookup", "android:bind_recycle_Old_views"], requireAll = false)
    fun onBindRecyclerView(recyclerView: RecyclerView,
                           adapter: RecyclerView.Adapter<*>?, layoutManager: RecyclerView.LayoutManager?,
                           spanSizeLookup: GridLayoutManager.SpanSizeLookup?, bindRecycleOldViews: Boolean = false) {
        recyclerView.layoutManager = layoutManager
        recyclerView.swapAdapter(adapter, !bindRecycleOldViews)
        if (spanSizeLookup != null && layoutManager != null && layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = spanSizeLookup
        }
    }

    /**
     * xml绑定RecyclerView itemDecoration
     *
     * @param recyclerView
     * @param itemDecoration
     */
    @kotlin.jvm.JvmStatic
    @BindingAdapter(value = ["android:bind_item_decoration"])
    fun onBindItemDecoration(recyclerView: RecyclerView, itemDecoration: RecyclerView.ItemDecoration?) {
        if (itemDecoration != null) {
            // 防止重复addDecoration
            recyclerView.removeItemDecoration(itemDecoration)
            recyclerView.addItemDecoration(itemDecoration)
        }
    }

    /**
     * xml绑定设置开启RecyclerView滚动水波纹
     *
     * @param recyclerView
     * @param isOverScroll
     */
    @kotlin.jvm.JvmStatic
    @BindingAdapter(value = ["android:bind_over_scroll"])
    fun onBindOverScroll(recyclerView: RecyclerView, isOverScroll: Boolean) {
        recyclerView.overScrollMode = if (isOverScroll) RecyclerView.OVER_SCROLL_ALWAYS else RecyclerView.OVER_SCROLL_NEVER
    }
}