package io.ganguo.viewmodel.core.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import io.ganguo.adapter.RecyclerDiffUtilAdapter
import io.ganguo.adapter.diffuitl.IDiffUtil
import io.ganguo.adapter.hodler.ItemViewHolder
import io.ganguo.log.core.Logger
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import java.util.*

/**
 * <pre>
 *    @author : leo
 *    time   : 2020/05/23
 *    desc   : Adapter DiffUtil Comparator
 * </pre>
 * @property parent BaseViewModel<*>
 * @property diffUtil IDiffUtil<BaseViewModel<*>>
 * @property oldDataList ArrayList<BaseViewModel<*>>
 * @constructor
 */
class ViewModelAdapter(context: Context, val parent: BaseViewModel<*>, private var diffUtil: IDiffUtil<BaseViewModel<*>>) : RecyclerDiffUtilAdapter<BaseViewModel<*>>(context, diffUtil) {
    override val oldDataList: ArrayList<BaseViewModel<*>> by lazy {
        ArrayList<BaseViewModel<*>>()
    }

    /**
     * binding Item data
     * @param vh ItemViewHolder<ViewDataBinding>
     * @param position Int
     */
    override fun onBindItemView(vh: ItemViewHolder<ViewDataBinding>, position: Int) {
        ViewModelHelper.bind(this, vh, get(position))
    }

    /**
     * remove data and refresh
     * @param viewModel BaseViewModel<*>
     * @param isRefresh Boolean
     */
    fun remove(viewModel: BaseViewModel<ViewInterface<*>>, isRefresh: Boolean) {
        var index = -1
        if (isRefresh) {
            index = indexOf(viewModel)
        }
        remove(viewModel)
        if (index != -1) {
            notifyItemRemoved(index)
        }
    }

    /**
     * Add data and refresh
     * @param viewModel BaseViewModel<*>
     * @param isRefresh Boolean
     */
    fun add(viewModel: BaseViewModel<ViewInterface<*>>, isRefresh: Boolean) {
        add(viewModel)
        if (isRefresh) {
            notifyItemInserted(itemCount)
        }
    }


}