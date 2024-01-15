package io.ganguo.mvvm.adapter

import android.content.Context
import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import io.ganguo.mvvm.adapter.diffuitl.ViewModelDiffUtilCallback
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.support.recyclerview.adapter.RecyclerListAdapter
import io.support.recyclerview.adapter.diffuitl.IDiffComparator
import io.support.recyclerview.adapter.hodler.ItemViewHolder

/**
 * <pre>
 *     @author : leo
 *     time   : 2020/05/23
 *     desc   : Adapter DiffUtil Comparator
 * </pre>
 * @property parent ViewModel<*>
 * @property diffUtil IDiffUtilHandler<ViewModel<*>>
 * @property oldDataList ArrayList<ViewModel<*>>
 * @constructor
 */
open class ViewModelAdapter(context: Context, val parent: ViewModel<*>, diffCallback: DiffUtil.ItemCallback<ViewModel<*>> = COMPARATOR) :
        RecyclerListAdapter<ViewModel<*>, ViewDataBinding>(context, diffCallback) {
    companion object {
        private val COMPARATOR = ViewModelDiffUtilCallback<ViewModel<*>>()
    }

    /**
     * binding Item data
     * @param vh ItemViewHolder<ViewDataBinding>
     * @param position Int
     */
    override fun onBindViewHolder(holder: ItemViewHolder<ViewDataBinding>, position: Int) {
        val item = getItem(position)
        if (item !is IDiffComparator<*>) {
            Log.w(item.javaClass.simpleName, "${item.javaClass.simpleName} needs to implement IDiffComparator interface and handle judgment rules, so the list performance will be better！")
        }
        ViewModelHelper.bind(this, holder, item)
    }

    fun add(viewModel: ViewModel<*>) {
        val newDatas = currentList.toMutableList()
        newDatas.add(viewModel)
        submitList(newDatas)
    }

    fun add(index: Int, viewModel: ViewModel<*>) {
        val newDatas = currentList.toMutableList()
        newDatas.add(index, viewModel)
        submitList(newDatas)
    }

    fun addAll(viewModels: List<ViewModel<*>>) {
        val newDatas = currentList.toMutableList()
        newDatas.addAll(viewModels)
        submitList(newDatas)
    }

    fun addAll(vararg viewModels: ViewModel<*>) {
        val newDatas = currentList.toMutableList()
        newDatas.addAll(viewModels)
        submitList(newDatas)
    }

    fun addAll(index: Int, viewModels: List<ViewModel<*>>) {
        val newDatas = currentList.toMutableList()
        newDatas.addAll(index, viewModels)
        submitList(newDatas)
    }

    fun remove(viewModel: ViewModel<*>) {
        if (!currentList.contains(viewModel)) {
            return
        }
        val newDatas = currentList.toMutableList()
        newDatas.remove(viewModel)
        submitList(newDatas)
    }

    fun remove(index: Int) {
        if (index !in 0..currentList.size) {
            return
        }
        val newDatas = currentList.toMutableList()
        newDatas.removeAt(index)
        submitList(newDatas)
    }

    fun clear() {
        submitList(null)
    }
}
