package io.ganguo.core.viewmodel.common.frame

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.ViewStubProxy
import io.ganguo.core.R
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.state.core.IStateViewHelper
import io.ganguo.state.core.StateViewHelper

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/14
 *   @desc   : HeaderContentFooterVModel
 * </pre>
 *
 * 适用页面结构: Header - container - Footer
 * 0、需要子类继承该类，方可使用
 * 1、无须自己实现整体的页面结构，但内容部分的布局需要自己实现和绑定
 * 2、头部和底部是可选的，根据页面结构，决定是否添加对应ViewModel即可。
 * 3、顶层父类都有实现StateViewModel，默认支持页面Loading、Empty、Error、NetWorkError等状态切换。
 * 4、contentLayout 和 contentViewModel 的高度必须设置为0，否则无法撑开，页面高度会显示异常。（原因参考：frame_header_content_footer.xml，与ConstraintLayout用法有关）
 */
@Suppress("UNCHECKED_CAST")
abstract class HeaderContentFooterVModel<V : ViewInterface<FrameHeaderContentFooterBinding>, M : ViewModel<*>, B : ViewDataBinding>(
        stateViewHelper: IStateViewHelper = StateViewHelper())
    : HeaderFooterVModel<FrameHeaderContentFooterBinding, V>(stateViewHelper) {
    override val layoutId: Int by lazy {
        R.layout.frame_header_content_footer
    }
    override val headerStub: ViewStubProxy by lazy {
        viewIF.binding.vsHeader
    }
    override val footerStub: ViewStubProxy by lazy {
        viewIF.binding.vsFooter
    }
    override val stateLayout: ViewGroup by lazy {
        createViewGroup(R.layout.container_state_frame_layout, viewIF.binding.vsState)
    }
    private val contentStub: ViewStubProxy by lazy {
        viewIF.binding.vsContent
    }
    private val contentBinding: B by lazy {
        inflateToViewBinding(contentStub, contentLayoutId) as B
    }
    private val contentBindingCreator: () -> B = {
        contentBinding
    }
    protected abstract val contentLayoutId: Int
    protected abstract val contentViewModel: M


    /**
     * Lazy loading layout
     */
    override fun inflateViewStub() {
        super.inflateViewStub()
        initContent(contentBindingCreator)
    }

    /**
     * ViewDataBinding bind to Content
     * @param binding C ContentViewModel的Binding类
     */
    protected open fun initContent(bindingCreator: () -> B) {
        ViewModelHelper.bind(bindingCreator.invoke(), this, contentViewModel)
    }


}
