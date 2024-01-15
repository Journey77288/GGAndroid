package io.ganguo.core.viewmodel.common.frame

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.ViewStubProxy
import io.ganguo.core.R
import io.ganguo.core.http.ViewStubHandler
import io.ganguo.core.http.ViewStubHelper
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.state.core.IStateViewHelper

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/13
 *   @desc   : Base HeaderFooterVModel
 * </pre>
 * 注意事项:
 *  0、需要继承，并自行实现页面布局，并实现headerStub和footerStub属性
 *  1、顶层父类都有实现StateViewModel，默认支持页面Loading、Empty、Error、NetWorkError等状态切换。（状态绑定对应控件，需自己实现，实现方式参考：HeaderContentFooterVModel）
 */
abstract class HeaderFooterVModel<T : ViewDataBinding, V : ViewInterface<T>>(stateViewHelper: IStateViewHelper)
    : StateViewModel<V>(stateViewHelper), ViewStubHandler by ViewStubHelper {
    abstract val headerStub: ViewStubProxy
    abstract val footerStub: ViewStubProxy
    private val headerViewCreator: () -> ViewGroup = {
        headerViewGroup
    }
    private val footerViewCreator: () -> ViewGroup = {
        footerViewGroup
    }
    private val headerViewGroup: ViewGroup by lazy {
        createViewGroup(R.layout.container_linear_layout, headerStub)
    }
    private val footerViewGroup: ViewGroup by lazy {
        createViewGroup(R.layout.container_linear_layout, footerStub)
    }



    /**
     * ViewModel onAttach
     */
    override fun onAttach() {
        super.onAttach()
        inflateViewStub()
    }

    /**
     * Lazy loading layout
     */
    protected open fun inflateViewStub() {
        initHeader(headerViewCreator)
        initFooter(footerViewCreator)
    }

    /**
     * ViewDataBinding bind to Header
     * @param header Function0<ViewGroup>
     */
    open fun initHeader(header: () -> ViewGroup) {

    }

    /**
     * ViewDataBinding bind to Footer
     * @param footer Function0<ViewGroup>
     */
    open fun initFooter(footer: () -> ViewGroup) {

    }

}
