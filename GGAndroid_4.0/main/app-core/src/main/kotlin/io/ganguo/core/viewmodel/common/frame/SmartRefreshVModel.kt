package io.ganguo.core.viewmodel.common.frame

import android.view.View
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.ganguo.core.R
import io.ganguo.core.databinding.WidgetSmartRefreshLayoutBinding
import io.ganguo.core.viewmodel.BaseViewModel
import io.ganguo.core.viewmodel.common.widget.RecyclerVModel
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper

/**
 * <pre>
 *   @author : leo
 *   @time : 2020/07/13
 *   @desc : SmartRefreshWrapLayout ViewModel
 * </pre>
 */
open class SmartRefreshVModel<T : ViewInterface<WidgetSmartRefreshLayoutBinding>>(private var recyclerVModel: RecyclerVModel<*, *>) : BaseViewModel<T>() {
    override val layoutId: Int = R.layout.widget_smart_refresh_layout
    open val smartRefresh: SmartRefreshLayout by lazy {
        viewIF.binding.swLayout
    }

    override fun onViewAttached(view: View) {
        ViewModelHelper.bind(viewIF.binding.includeRecycler, this, recyclerVModel)
    }
}
