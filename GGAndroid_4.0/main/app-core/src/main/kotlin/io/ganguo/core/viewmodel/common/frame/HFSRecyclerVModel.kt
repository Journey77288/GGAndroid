package io.ganguo.core.viewmodel.common.frame

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.ViewStubProxy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import io.ganguo.core.R
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.databinding.WidgetRecyclerViewBinding
import io.ganguo.core.databinding.WidgetSmartRefreshLayoutBinding
import io.ganguo.core.viewmodel.common.widget.RecyclerVModel
import io.ganguo.mvvm.adapter.ViewModelAdapter
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.state.core.IStateViewHelper
import io.ganguo.state.core.StateViewHelper

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/14
 *   @desc   : HFSRecyclerVModel
 * </pre>
 * 页面结构: Header - SmartRefreshLayout(RecyclerView) - Footer
 * 0、需要子类继承该类，方可使用
 * 1、头部和底部是可选的，根据页面结构，决定是否添加对应ViewModel即可。
 * 2、刷新和分页加载，也可根据场景，进行开启/禁用
 * 3、顶层父类都有实现StateViewModel，默认支持页面Loading、Empty、Error、NetWorkError等状态切换
 * 4、分页加载完成后需要更新SmartRefreshLayout的enableLoadMore状态，建议在接口请求Complete回调中调toggleEmptyView和finishLoadMore方法
 */
abstract class HFSRecyclerVModel<V : ViewInterface<FrameHeaderContentFooterBinding>>(stateViewHelper: IStateViewHelper = StateViewHelper())
    : HeaderListFooterVModel<V, SmartRefreshVModel<*>, WidgetSmartRefreshLayoutBinding>(stateViewHelper), OnRefreshLoadMoreListener {
    override val contentLayoutId: Int = R.layout.widget_smart_refresh_layout
    override val headerStub: ViewStubProxy by lazy {
        viewIF.binding.vsHeader
    }
    override val footerStub: ViewStubProxy by lazy {
        viewIF.binding.vsFooter
    }
    private val smartRefresh: SmartRefreshLayout by lazy {
        contentViewModel.smartRefresh
    }
    private val recyclerVModel: RecyclerVModel<*, *> by lazy {
        createRecycleVModel()
    }
    override val contentViewModel: SmartRefreshVModel<*> by lazy {
        createRefreshVModel()
    }
    override val adapter: ViewModelAdapter by lazy {
        recyclerVModel.adapter
    }
    override val recyclerView: RecyclerView by lazy {
        recyclerVModel.recyclerView
    }

    /**
     * create SmartRefreshVModel，Height must be set to 0
     * @return SmartRefreshVModel<*>
     */
    protected open fun createRefreshVModel(): SmartRefreshVModel<*> = let {
        SmartRefreshVModel<ViewInterface<WidgetSmartRefreshLayoutBinding>>(recyclerVModel)
                .apply {
                    height(0)
                }
    }

    /**
     * create RecyclerVModel，Height must be set to MATCH_PARENT
     * @return RecyclerVModel<ViewInterface<WidgetRecyclerViewBinding>>
     */
    override fun createRecycleVModel(): RecyclerVModel<*, *> = let {
        RecyclerVModel
                .linerLayout<WidgetRecyclerViewBinding, ViewInterface<WidgetRecyclerViewBinding>>(
                        context, LinearLayoutManager.VERTICAL, ViewGroup.LayoutParams.MATCH_PARENT
                )
    }

    /**
     * ViewModel attach
     */
    override fun onAttach() {
        super.onAttach()
        initSmartRefreshLayout(smartRefresh)
        initSmartRefreshConfig(smartRefresh)
    }


    /**
     * SmartRefreshLayout initializes the Content View
     * @param smartRefresh SmartRefreshLayout
     */
    protected open fun initSmartRefreshLayout(smartRefresh: SmartRefreshLayout) {
        initSmartRefreshHeader(smartRefresh)
        initSmartRefreshContent(smartRefresh)
        initSmartRefreshFooter(smartRefresh)
        smartRefresh.setOnRefreshLoadMoreListener(this)
    }

    /**
     * SmartRefreshLayout initializes the configuration
     * @param smartRefresh SmartRefreshLayout
     */
    protected open fun initSmartRefreshConfig(smartRefresh: SmartRefreshLayout) {
        smartRefresh.setEnableRefresh(true)
        smartRefresh.setEnableLoadMore(false)
        smartRefresh.setEnableAutoLoadMore(true)
        smartRefresh.setEnableLoadMoreWhenContentNotFull(false)
        smartRefresh.setEnableOverScrollDrag(false)
        smartRefresh.setEnableOverScrollBounce(false)
    }

    /**
     * SmartRefreshLayout initializes the configuration
     * @param smartRefresh SmartRefreshLayout
     */
    protected open fun initSmartRefreshHeader(smartRefresh: SmartRefreshLayout) {
    }

    /**
     * SmartRefreshLayout initializes the configuration
     * @param smartRefresh SmartRefreshLayout
     */
    protected open fun initSmartRefreshFooter(smartRefresh: SmartRefreshLayout) {
    }

    /**
     * SmartRefreshLayout initializes the configuration
     * @param smartRefresh SmartRefreshLayout
     */
    protected open fun initSmartRefreshContent(smartRefresh: SmartRefreshLayout) {

    }

    /**
     * Toggle empty data state
     */
    override fun toggleEmptyView() {
        super.toggleEmptyView()
        smartRefresh.finishLoadMore()
        smartRefresh.finishRefresh()
    }

    /**
     * Finish load more data
     */
    fun finishLoadMore(isLastPage: Boolean) {
        if (isLastPage) {
            smartRefresh.finishLoadMoreWithNoMoreData()
            smartRefresh.setEnableLoadMore(false)
        } else {
            smartRefresh.setEnableLoadMore(true)
        }
    }

    /**
     * Set Enable Load More
     *
     * @param enable Boolean
     */
    fun setEnableLoadMore(enable: Boolean) {
        if (isAttach()) {
            smartRefresh.setEnableLoadMore(enable)
        }
    }

    /**
     * list loadMore callback
     * @param refreshLayout RefreshLayout
     */
    override fun onLoadMore(refreshLayout: RefreshLayout) {
    }

    /**
     * list refresh callback
     * @param refreshLayout RefreshLayout
     */
    override fun onRefresh(refreshLayout: RefreshLayout) {
    }
}
