package io.ganguo.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import io.ganguo.adapter.diffuitl.IDiffUtil
import io.ganguo.adapter.hodler.LayoutId

/**
 * <pre>
 *    @author : leo
 *    time   : 2020/05/23
 *    desc   : Support Diff algorithm RecyclerListAdapter
 * </pre>
 *
 * @param T : LayoutId
 * @param B : ViewDataBinding
 * @property diffUtil IDiffUtil<T>
 * @property oldDataList MutableList<T>
 * @constructor
 */
abstract class RecyclerDiffUtilAdapter<T : LayoutId>(context: Context, private var diffUtil: IDiffUtil<T>) : RecyclerListAdapter<T, ViewDataBinding>(context) {
    abstract val oldDataList: ArrayList<T>
    override fun getItemLayoutId(position: Int): Int {
        return get(position).layoutId
    }

    /**
     * Diff notifyDataSetChanged()
     * @see item must be implements [io.ganguo.adapter.diffuitl.IComparator] interface，Otherwise, the refresh will flash
     */
    fun notifySetDataDiffChanged() {
        diffUtil.notifyDataSetChanged(this, oldDataList, data)
    }

    /**
     * Release resources
     */
    override fun release() {
        diffUtil.release()
    }
}