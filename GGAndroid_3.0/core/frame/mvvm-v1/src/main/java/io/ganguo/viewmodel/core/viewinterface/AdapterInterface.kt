package io.ganguo.viewmodel.core.viewinterface

import androidx.databinding.ViewDataBinding
import io.ganguo.adapter.hodler.ItemViewHolder
import io.ganguo.viewmodel.core.adapter.ViewModelAdapter


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : RecyclerView Adapter Interface
 * </pre>
 */
interface AdapterInterface<T : ViewDataBinding> : ViewInterface<T> {
    val adapter: ViewModelAdapter
    val viewHolder: ItemViewHolder<T>
}