package io.ganguo.viewmodel.pack.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import io.ganguo.utils.util.Views
import io.ganguo.viewmodel.R
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding
import io.ganguo.viewmodel.pack.common.base.BaseHFRViewModel

/**
 * Header Footer SwipeRefresh RecyclerView 组合ViewModel
 * Created by Roger on 6/22/16.
 */
abstract class HFSRecyclerViewModel<T : ViewInterface<IncludeHfSwipeRecyclerBinding>> : BaseHFRViewModel<IncludeHfSwipeRecyclerBinding, T>(), OnRefreshLoadMoreListener {
    override val layoutId: Int
        get() = R.layout.include_hf_swipe_recycler
    override val headerContainer: ViewGroup by lazy {
        viewInterface.binding.llyHeader
    }

    override val footerContainer: ViewGroup by lazy {
        viewInterface!!.binding!!.llyFooter
    }

    override val statusContainer: ViewGroup by lazy {
        viewInterface.binding.llyState
    }
    override val recyclerRootView: ViewDataBinding by lazy {
        viewInterface.binding.includeRecycler as ViewDataBinding
    }
    val smartRefreshLayout: SmartRefreshLayout by lazy {
        viewInterface.binding.refreshLayout
    }

    override fun onAttach() {
        super.onAttach()
        initSmartRefresh(smartRefreshLayout)
    }

    /**
     * init SmartRefreshLayout
     */
    protected open fun initSmartRefresh(refreshLayout: RefreshLayout) {
        refreshLayout.setEnableRefresh(true)
        refreshLayout.setEnableLoadMore(true)
        refreshLayout.setEnableAutoLoadMore(true)
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false)
        refreshLayout.setEnableOverScrollDrag(false)
        refreshLayout.setEnableOverScrollBounce(false)
        refreshLayout.setOnRefreshLoadMoreListener(this)
    }

    /**
     * 设置是否开启LoadMore
     *
     * @return
     */
    fun setEnableLoadMore(isEnable: Boolean) {
        if (isAttach) {
            smartRefreshLayout.setEnableLoadMore(isEnable)
        }
    }

    /**
     * 设置是否开启下拉刷新
     *
     * @return
     */
    fun setEnableRefresh(isEnable: Boolean) {
        if (isAttach) {
            smartRefreshLayout.setEnableRefresh(isEnable)
        }
    }

    override fun toggleEmptyView() {
        if (!isAttach) {
            return
        }
        smartRefreshLayout.finishLoadMore()
        smartRefreshLayout.finishRefresh()
        super.toggleEmptyView()
    }


    /**
     * 下拉刷新函数
     *
     * @see {@link OnRefreshLoadMoreListener.onRefresh
     */
    override fun onRefresh(refreshLayout: RefreshLayout) {}

    /**
     * 上拉加载函数
     *
     * @see {@link OnRefreshLoadMoreListener.onLoadMore
     */
    override fun onLoadMore(refreshLayout: RefreshLayout) { //进入LoadMore函数后，强制停止RecyclerView滑动，避免滑动过快，出现多次LoadMore的情况！
        Views.onCancelRecyclerViewScroll(recyclerView)
    }
}