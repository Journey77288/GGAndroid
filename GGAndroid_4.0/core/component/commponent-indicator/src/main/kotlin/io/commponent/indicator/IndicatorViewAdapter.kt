package io.commponent.indicator

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/23
 *   @desc   : IndicatorView Adapter
 * </pre>
 */
class IndicatorViewAdapter(private val indicator: Indicator) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(indicator.createHolderView(parent, viewType)) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return indicator.getCount()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
