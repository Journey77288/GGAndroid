package io.support.recyclerview.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.support.recyclerview.adapter.hodler.ItemViewHolder

/**
 * <pre>
 * author : leo
 * time   : 2020/05/23
 * desc   : In support of dataBinding RecyclerView.Adapter
 * </pre>
 *
 * @param B : ViewDataBinding?
 * @property context Context
 * @property inflater LayoutInflater
 * @constructor
 */
abstract class RecyclerViewAdapter<T, B : ViewDataBinding>(
    val context: Context,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, ItemViewHolder<B>>(diffCallback) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    /**
     *
     * create item viewHolder
     * @param parent ViewGroup
     * @param viewType Int
     * @return ItemViewHolder<B>
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<B> {
        return ItemViewHolder(DataBindingUtil.inflate(inflater, viewType, parent, false))
    }

    /**
     * item type == item layoutId
     * @param position Int
     * @return Int
     */
    override fun getItemViewType(position: Int): Int {
        return getItemLayoutId(position)
    }

    /**
     *  item layoutId
     * @param position Int
     * @return Int
     */
    @LayoutRes
    protected abstract fun getItemLayoutId(position: Int): Int
}
