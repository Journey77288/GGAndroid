package io.ganguo.core.viewmodel.common.frame

import android.view.ViewGroup
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.state.core.IStateViewGatherCreator
import io.ganguo.state.core.IStateViewHandler
import io.ganguo.state.core.IStateViewHelper

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/12
 *   @desc   : 支持状态切换ViewModel，该ViewModel支持Empty、Loading、Error、NetWorkError等状态
 * </pre>
 * @see addLoadingViewCreator(creator: StateViewService) 添加Loading状态样式
 * @see addEmptyViewCreator(creator: StateViewService) 添加空布局状态样式
 * @see addErrorViewCreator(creator: StateViewService) 添加页面错误状态样式
 * @see addNetworkErrorViewCreator(creator: StateViewService) 添加网络错误状态样式
 * @see addCreator(stateViewKey: String, creator: StateViewService) 添加除以上四种状态以外的样式，需要自己根据`stateViewKey`维护显示/隐藏状态
 */
abstract class StateViewModel<V : ViewInterface<*>>(private val stateViewHelper: IStateViewHelper)
    : ViewModel<V>(), IStateViewHandler by stateViewHelper, IStateViewGatherCreator by stateViewHelper {
    abstract val stateLayout: ViewGroup

    /**
     * Bind StateLayout
     */
    override fun onAttach() {
        super.onAttach()
        initStateLayout()
    }


    /**
     * bind stateLayout to stateHelper
     */
    protected open fun initStateLayout() {
        stateViewHelper.bindStateLayout(stateLayout)
    }
}
