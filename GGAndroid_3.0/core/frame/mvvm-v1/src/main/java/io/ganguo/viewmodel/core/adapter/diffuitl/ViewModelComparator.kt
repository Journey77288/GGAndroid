package io.ganguo.viewmodel.core.adapter.diffuitl

import io.ganguo.adapter.diffuitl.DataDiffComparator
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * <pre>
 *     @author : Leo
 *     time   : 2020/05/28
 *     desc   : ViewModel AdapterComparator
 * </pre>
 */
class ViewModelComparator<T : BaseViewModel<*>>(oldData: List<T>, newData: List<T>) : DataDiffComparator<T>(oldData, newData) {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val isClassSame = super.areItemsTheSame(oldItemPosition, newItemPosition)
        val oldViewModel: T = oldData[oldItemPosition]
        val newViewModel: T = newData[newItemPosition]
        val isLayoutSame = oldViewModel.layoutId == newViewModel.layoutId
        return isClassSame && isLayoutSame
    }
}