package io.ganguo.core.adapter

import android.content.Context
import android.util.Log
import androidx.databinding.ViewDataBinding
import io.component.banner.BannerPositionDelegate
import io.ganguo.mvvm.adapter.ViewModelAdapter
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.support.recyclerview.adapter.diffuitl.IDiffComparator
import io.support.recyclerview.adapter.hodler.ItemViewHolder

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/05
 *   @desc   :
 * </pre>
 */
class BannerViewModelAdapter(context: Context, parent: ViewModel<*>) : ViewModelAdapter(context, parent), BannerPositionDelegate {
    private var increaseCount: Int = 4


    /**
     * binding Item data
     * @param vh ItemViewHolder<ViewDataBinding>
     * @param position Int
     */
    override fun onBindViewHolder(holder: ItemViewHolder<ViewDataBinding>, position: Int) {
        val realPosition = getRealPosition(position)
        val item = getItem(realPosition)
        Log.i("Tag", "onBindViewHolder: ${position}   ${realPosition}")
        if (item !is IDiffComparator<*>) {
            Log.w(item.javaClass.simpleName, "${item.javaClass.simpleName} needs to implement IDiffComparator interface and handle judgment rules, so the list performance will be better！")
        }
        ViewModelHelper.bind(this, holder, item)
    }

    /**
     * page count
     * @return Int
     */
    override fun getItemCount(): Int {
        return getPageCount()
    }

    /**
     * The real number
     * @return Int
     */
    override fun getRealCount(): Int = let {
        currentList.size
    }

    /**
     * Page Increase Count
     * @return Int
     */
    override fun getIncreaseCount(): Int = let {
        increaseCount
    }


    /**
     * Set Page Increase Count
     * @param count Int
     */
    override fun setIncreaseCount(count: Int) {
        this.increaseCount = count
    }

    override fun getItemLayoutId(position: Int): Int {
        return currentList[position.coerceIn(0, currentList.size - 1)].layoutId
    }
}
