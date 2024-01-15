package io.support.recyclerview.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import io.support.recyclerview.adapter.hodler.LayoutId

/**
 * <pre>
 * author : leo
 * time   : 2020/05/23
 * desc   : RecyclerView List Adapter
 * </pre>
 *
 *
 * @param T
 * @param B : ViewDataBinding
 * @property context Context
 * @property diffCallback DiffUtil.ItemCallback<T>
 * @constructor
 */
abstract class RecyclerListAdapter<T : LayoutId, B : ViewDataBinding>(
    context: Context,
    diffCallback: DiffUtil.ItemCallback<T>
) : RecyclerViewAdapter<T, B>(context, diffCallback) {

    override fun getItemLayoutId(position: Int): Int {
        return currentList[position].layoutId
    }

}
