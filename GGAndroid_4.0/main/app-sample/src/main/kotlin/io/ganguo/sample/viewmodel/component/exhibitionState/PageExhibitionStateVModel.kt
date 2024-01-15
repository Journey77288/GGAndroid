package io.ganguo.sample.viewmodel.component.exhibitionState

import android.view.View
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.PageLoadingVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.sample.bean.ExhibitionState

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/19
 *     desc   : Exhibition State Page ViewModel
 * </pre>
 */
class PageExhibitionStateVModel(private val state: ExhibitionState) : HFRecyclerVModel<ViewInterface<FrameHeaderContentFooterBinding>>() {

    init {
        addLoadingViewCreator(PageLoadingVModel(this))
        val statePageVModel = PageStateVModel(state, this)
        addEmptyViewCreator(statePageVModel)
        addErrorViewCreator(statePageVModel)
        addNetworkErrorViewCreator(statePageVModel)
    }

    override fun onViewAttached(view: View) {
        initStateView()
    }

    /**
     * 初始化状态
     */
    private fun initStateView() {
        when (state) {
            ExhibitionState.LOADING -> showLoadingView()
            ExhibitionState.COMPLETE -> showContentView()
            ExhibitionState.EMPTY -> showEmptyView()
            ExhibitionState.ERROR -> showErrorView()
            ExhibitionState.NETWORK_ERROR -> showNetWorkErrorView()
        }
    }
}