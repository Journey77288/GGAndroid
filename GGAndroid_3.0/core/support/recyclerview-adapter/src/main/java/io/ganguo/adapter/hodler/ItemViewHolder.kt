package io.ganguo.adapter.hodler

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 * author : leo
 * time   : 2020/05/23
 * desc   : Base RecyclerView.ViewHolder
 * </pre>
 *
 * @param B : ViewDataBinding?
 * @property binding B
 * @constructor
 */
class ItemViewHolder<B : ViewDataBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)