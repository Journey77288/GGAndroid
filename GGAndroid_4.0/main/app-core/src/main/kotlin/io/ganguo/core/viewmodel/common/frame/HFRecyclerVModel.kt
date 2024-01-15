package io.ganguo.core.viewmodel.common.frame

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ganguo.core.R
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.databinding.WidgetRecyclerViewBinding
import io.ganguo.core.viewmodel.common.widget.RecyclerVModel
import io.ganguo.mvvm.adapter.ViewModelAdapter
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.state.core.IStateViewHelper
import io.ganguo.state.core.StateViewHelper

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/14
 *   @desc   : HFRecyclerVModel
 * </pre>
 * 适用页面结构: Header - RecyclerView - Footer (支持切换状态)
 */

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/14
 *   @desc   : HFSRecyclerVModel
 * </pre>
 * 适用页面结构: Header - RecyclerView - Footer
 * 0、需要子类继承该类，方可使用
 * 1、头部和底部是可选的，根据页面结构，决定是否添加对应ViewModel即可。
 * 2、顶层父类都有实现StateViewModel，默认支持页面Loading、Empty、Error、NetWorkError等状态切换
 */
abstract class HFRecyclerVModel<V : ViewInterface<FrameHeaderContentFooterBinding>>(stateViewHelper: IStateViewHelper = StateViewHelper())
    : HeaderListFooterVModel<V, RecyclerVModel<*, *>, WidgetRecyclerViewBinding>(stateViewHelper) {
    override val contentLayoutId: Int = R.layout.widget_recycler_view
    override val contentViewModel: RecyclerVModel<*, *> by lazy {
        createRecycleVModel()
    }
    override val adapter: ViewModelAdapter
        get() = let {
            contentViewModel.adapter
        }
    override val recyclerView: RecyclerView
        get() = let {
            contentViewModel.recyclerView
        }

    /**
     * create RecyclerVModel
     * @return RecyclerVModel<ViewInterface<WidgetRecyclerViewBinding>>
     */
    override fun createRecycleVModel(): RecyclerVModel<*, *> = let {
        RecyclerVModel
                .linerLayout<
                        WidgetRecyclerViewBinding, ViewInterface<WidgetRecyclerViewBinding>>(
                        context, LinearLayoutManager.VERTICAL, 0
                )
    }


}
