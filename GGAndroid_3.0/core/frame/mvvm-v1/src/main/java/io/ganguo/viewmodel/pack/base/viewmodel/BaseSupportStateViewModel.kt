package io.ganguo.viewmodel.pack.base.viewmodel

import android.view.ViewGroup
import io.ganguo.state.Constants
import io.ganguo.state.IStateViewHandler
import io.ganguo.state.core.StateViewHelper
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.pack.helper.state.StateViewCreatorFactory

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/06
 *     desc   : 支持页面状态切换的ViewModel
 *            : 默认封装[Loading、网络错误、一般错误、空数据]页面状态
 * </pre>
 * @property stateLayout 状态布局父容器
 * @property stateHelper 状态切换工具类
 */
abstract class BaseSupportStateViewModel<T : ViewInterface<*>> : BaseViewModel<T>(), IStateViewHandler {
    abstract val stateLayout: ViewGroup
    override val stateHelper: StateViewHelper by lazy {
        StateViewHelper(stateLayout).apply {
            addCreator(Constants.NETWORK_ERROR, StateViewCreatorFactory.netWorkErrorViewCreator(this@BaseSupportStateViewModel))
            addCreator(Constants.ERROR, StateViewCreatorFactory.errorViewCreator(this@BaseSupportStateViewModel))
            addCreator(Constants.EMPTY, StateViewCreatorFactory.emptyViewCreator(this@BaseSupportStateViewModel))
            addCreator(Constants.LOADING, StateViewCreatorFactory.loadingViewCreator(this@BaseSupportStateViewModel))
        }
    }


    override fun onAttach() {
        super.onAttach()
        showLoadingView()
    }
}