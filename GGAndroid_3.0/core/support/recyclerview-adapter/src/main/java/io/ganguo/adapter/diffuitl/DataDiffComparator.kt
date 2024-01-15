package io.ganguo.adapter.diffuitl

import androidx.recyclerview.widget.DiffUtil
import java.util.*

/**
 * <pre>
 *    @author : leo
 *    time   : 2020/05/23
 *    desc   : Adapter DiffUtil Comparator
 * </pre>
 * @param T
 * @property oldData MutableList<T>
 * @property newData MutableList<T>
 * @constructor
 */
open class DataDiffComparator<T>(oldData: List<T>, newData: List<T>) : DiffUtil.Callback() {
    val oldData: List<T> = ArrayList(oldData)
    val newData: List<T> = ArrayList(newData)

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldData[oldItemPosition]
        val newItem = newData[newItemPosition]
        val isComparator = oldItem is IComparator<*> && newItem is IComparator<*>
        return isComparator && oldItem == newItem
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldData[oldItemPosition] as IComparator<T>
        val newItem = newData[newItemPosition] as IComparator<T>
        return oldItem.itemEquals(newItem.itemData)
    }

}