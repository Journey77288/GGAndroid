package io.ganguo.core.viewmodel.common.component

import android.content.Context
import android.view.View
import io.ganguo.core.R
import io.ganguo.core.databinding.ComponentPageLoadingBinding
import io.ganguo.log.core.Logger
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.state.core.StateViewService

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/15
 *   @desc   :Page Loading ViewModel
 * </pre>
 */
class PageLoadingVModel(private val parent: ViewModel<*>) : ViewModel<ViewInterface<ComponentPageLoadingBinding>>(), StateViewService {
    override val layoutId: Int by lazy {
        R.layout.component_page_loading
    }

    override fun onViewAttached(view: View) {
    }

    /**
     * 创建Loading StateView
     * @param context Context
     * @return View
     */
    override fun createStateView(context: Context): View = let {
        ViewModelHelper.bind(context, parent, this).rootView
    }

    /**
     * 根据隐藏/显示，做一些资源重置/释放操作
     * @param isVisible Boolean
     */
    override fun onStateViewVisibleChanged(isVisible: Boolean) {
        Logger.d("onStateViewVisibleChanged:${isVisible}")
        if (!isAttach()) {
            return
        }
        viewIF.binding.mpBar.isIndeterminate = isVisible
    }

}
