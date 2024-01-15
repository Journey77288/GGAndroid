package io.ganguo.adapter.diffuitl

import io.ganguo.adapter.RecyclerDiffUtilAdapter
import io.ganguo.adapter.hodler.LayoutId


/**
 * <pre>
 *    @author : leo
 *    time   : 2020/05/23
 *    desc   : Adapter DiffUtil
 * </pre>
 * @param T
 */
interface IDiffUtil<T : LayoutId> {
    fun notifyDataSetChanged(diffAdapter: RecyclerDiffUtilAdapter<T>, oldData: MutableList<T>, data: MutableList<T>)

    fun release()
}