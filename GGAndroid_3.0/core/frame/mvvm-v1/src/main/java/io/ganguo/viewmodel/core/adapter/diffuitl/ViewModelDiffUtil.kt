package io.ganguo.viewmodel.core.adapter.diffuitl

import androidx.recyclerview.widget.DiffUtil
import io.ganguo.adapter.RecyclerDiffUtilAdapter
import io.ganguo.adapter.diffuitl.IDiffUtil
import io.ganguo.viewmodel.core.BaseViewModel
import kotlinx.coroutines.*

/**
 * <pre>
 *     @author : Leo
 *     time   : 2020/05/28
 *     desc   : ViewModel AdapterComparator
 * </pre>
 */
class ViewModelDiffUtil : IDiffUtil<BaseViewModel<*>>, CoroutineScope by MainScope() {
    /**
     * 使用DiffUtil算法实现adapter高效刷新
     *
     * @param data        新增数据后的，全部数据集合
     * @param oldData     添加数据前的数据集合
     * @param diffAdapter adapter对象引用
     */
    override fun notifyDataSetChanged(diffAdapter: RecyclerDiffUtilAdapter<BaseViewModel<*>>, oldData: MutableList<BaseViewModel<*>>, data: MutableList<BaseViewModel<*>>) {
        launch(Dispatchers.Main) {
            if (oldData.isEmpty()) {
                oldData.addAll(data)
            }
            var result = withContext(Dispatchers.IO) {
                DiffUtil.calculateDiff(ViewModelComparator(oldData, data), true)
            }
            result.dispatchUpdatesTo(diffAdapter)
            oldData.clear()
            oldData.addAll(data)
        }
    }

    /**
     * 资源释放
     */
    override fun release() {
        cancel()
    }

}