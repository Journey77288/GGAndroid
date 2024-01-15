package io.ganguo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.ganguo.adapter.hodler.ItemViewHolder


/**
 * <pre>
 * author : leo
 * time   : 2020/05/23
 * desc   : In support of dataBinding RecyclerView.Adapter
 * </pre>
 *
 * @param B : ViewDataBinding?
 * @property context Context
 * @property itemInflater LayoutInflater
 * @constructor
 */
abstract class RecyclerBindingAdapter< B : ViewDataBinding>(val context: Context) : RecyclerView.Adapter<ItemViewHolder<B>>() {
    private val itemInflater: LayoutInflater = LayoutInflater.from(context)

    /**
     *
     * create item viewHolder
     * @param parent ViewGroup
     * @param viewType Int
     * @return ItemViewHolder<B>
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<B> {
        return ItemViewHolder(DataBindingUtil.inflate(itemInflater, viewType, parent, false))
    }

    /**
     * binding Item ViewHolder
     * @param holder ItemViewHolder<B>
     * @param position Int
     */
    override fun onBindViewHolder(holder: ItemViewHolder<B>, position: Int) {
        onBindItemView(holder, position)
        holder.binding.executePendingBindings()
    }

    /**
     * binding Item data
     * @param vh ItemViewHolder<B>
     * @param position Int
     */
    abstract fun onBindItemView(vh: ItemViewHolder<B>, position: Int)

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


    /**
     * Release resources
     */
    abstract fun release()

}