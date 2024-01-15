package io.ganguo.mvvm.adapter.diffuitl

import io.ganguo.mvvm.viewmodel.ViewModel
import io.support.recyclerview.adapter.diffuitl.DiffUtilCallback
import io.support.recyclerview.adapter.diffuitl.IDiffComparator

/**
 * <pre>
 *     @author : Leo
 *     time   : 2020/05/28
 *     desc   : ViewModel AdapterComparator
 * </pre>
 */
class ViewModelDiffUtilCallback<T : ViewModel<*>> : DiffUtilCallback<T>() {

    /**
     * Whether it's the same item
     * @param oldItem T
     * @param newItem T
     * @return Boolean
     */
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        val isSameLayout = oldItem.layoutId == newItem.layoutId
        return oldItem == newItem && isSameLayout
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        if (oldItem is IDiffComparator<*> && newItem is IDiffComparator<*>) {
            return (oldItem as IDiffComparator<Any>).itemEquals((newItem as IDiffComparator<Any>).getItem())
        }
        return oldItem.equals(newItem)
    }
}
