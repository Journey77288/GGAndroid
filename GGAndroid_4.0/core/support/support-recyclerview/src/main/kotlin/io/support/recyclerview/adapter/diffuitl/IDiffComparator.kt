package io.support.recyclerview.adapter.diffuitl


/**
 * <pre>
 *     author : leo
 *     time   : Comparator interface
 *     desc   : 2020/05/23
 * </pre>
 * @param T
 * @property itemData T
 */
interface IDiffComparator<T : Any> {
    /**
     * item comparative method
     * @param t T
     * @return Boolean
     */
    fun itemEquals(t: T): Boolean

    /**
     * item data to be compared
     * @return
     */
    fun getItem(): T
}
