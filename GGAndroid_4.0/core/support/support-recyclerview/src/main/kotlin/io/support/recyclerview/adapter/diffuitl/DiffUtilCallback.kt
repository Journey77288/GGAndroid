package io.support.recyclerview.adapter.diffuitl

import androidx.recyclerview.widget.DiffUtil

/**
 * <pre>
 *     author : lucas
 *     time   : 2023/08/09
 *     desc   : DiffUtil 比较接口回调
 * </pre>
 */
abstract class DiffUtilCallback<T>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        val isComparator = oldItem is IDiffComparator<*> && newItem is IDiffComparator<*>
        return isComparator && oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        if (oldItem is IDiffComparator<*> && newItem is IDiffComparator<*>) {
            return (oldItem as IDiffComparator<T & Any>).itemEquals((newItem as IDiffComparator<T & Any>).getItem())
        }
        return false
    }
}