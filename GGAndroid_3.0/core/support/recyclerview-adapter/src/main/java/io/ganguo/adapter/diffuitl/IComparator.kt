package io.ganguo.adapter.diffuitl

import io.ganguo.adapter.hodler.LayoutId

/**
 * <pre>
 *     author : leo
 *     time   : Comparator interface
 *     desc   : 2020/05/23
 * </pre>
 * @param T
 * @property itemData T
 */
interface IComparator<T>:LayoutId {
    /**
     * item comparative method
     * @param t T
     * @return Boolean
     */
    fun itemEquals(t: T): Boolean

    /**
     * item data to be compared
     */
    val itemData: T
}