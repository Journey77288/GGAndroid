@file:JvmName("ViewStubBindingAdapter")


package io.ganguo.databinding

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/29
 *     desc   : ViewSub DataBinding Adapter
 * </pre>
 */


import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewStubProxy


/**
 * Binding sets the ViewStub layoutResource
 * @receiver ViewStubProxy
 * @param layoutId Int
 */
@BindingAdapter(
        value = ["android:bind_layout_id_to_view_stub"]
)
fun ViewStubProxy.bindLayoutResource(@LayoutRes layoutId: Int): ViewStubProxy {
    if (layoutId == 0) {
        return this
    }
    viewStub?.layoutResource = layoutId
    return this
}
