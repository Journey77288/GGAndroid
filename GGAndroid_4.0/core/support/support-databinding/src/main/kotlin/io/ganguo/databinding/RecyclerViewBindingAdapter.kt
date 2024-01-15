@file:JvmName("RecyclerViewBindingAdapter")


/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/29
 *     desc   : RecyclerView DataBinding Adapter
 * </pre>
 */

package io.ganguo.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * The binding is set to RecyclerView
 * @receiver  RecyclerView
 * @param newAdapter Adapter<*>?
 * @param newLayoutManager LayoutManager?
 * @param sizeLookup SpanSizeLookup?
 */
@BindingAdapter(
        value = [
            "android:bind_adapter_to_recycler_view",
            "android:bind_layout_manager_to_recycler_view",
            "android:bind_span_size_lookup_to_recycler_view"]
)
fun RecyclerView.bindSetting(
        newAdapter: RecyclerView.Adapter<*>?,
        newLayoutManager: RecyclerView.LayoutManager?,
        sizeLookup: GridLayoutManager.SpanSizeLookup?
) {
    if (newLayoutManager != null && newLayoutManager !== layoutManager && !newLayoutManager.isAttachedToWindow) {
        layoutManager = newLayoutManager
    }
    swapAdapter(newAdapter, false)
    if (layoutManager != null && layoutManager is GridLayoutManager && sizeLookup != null) {
        (layoutManager as GridLayoutManager).spanSizeLookup = sizeLookup
    }

}


/**
 * Bind ItemDecoration set to RecyclerView
 * @receiver  RecyclerView
 */
@BindingAdapter(value = ["android:bind_item_decoration_to_recycler_view"])
fun RecyclerView.bindItemDecoration(itemDecoration: RecyclerView.ItemDecoration?) {
    itemDecoration?.let {
        removeItemDecoration(itemDecoration)
        addItemDecoration(itemDecoration)
    }
}

/**
 * Binding setting whether to turn on RecyclerView water ripple effect
 * @receiver  RecyclerView
 * @param toOverScrollMode Int?
 */
@BindingAdapter(value = ["android:bind_over_scroll_mode_to_recycler_view"])
fun RecyclerView.bindOverScroll(toOverScrollMode: Int? = RecyclerView.OVER_SCROLL_ALWAYS) {
    overScrollMode = toOverScrollMode ?: RecyclerView.OVER_SCROLL_ALWAYS
}


/**
 * Binding setting whether to turn on RecyclerView water ripple effect
 * @receiver  RecyclerView
 * @param isOverScroll Boolean?
 */
@BindingAdapter(value = ["android:bind_over_scroll_boolean_to_recycler_view"])
fun RecyclerView.bindOverScroll(isOverScroll: Boolean?) {
    overScrollMode = if (isOverScroll == null || !isOverScroll) {
        RecyclerView.OVER_SCROLL_ALWAYS
    } else {
        RecyclerView.OVER_SCROLL_NEVER
    }
}
