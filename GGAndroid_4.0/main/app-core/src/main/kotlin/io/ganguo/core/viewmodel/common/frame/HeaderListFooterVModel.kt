package io.ganguo.core.viewmodel.common.frame

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.databinding.WidgetRecyclerViewBinding
import io.ganguo.core.viewmodel.common.widget.RecyclerVModel
import io.ganguo.mvvm.adapter.ViewModelAdapter
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.state.core.IStateViewHelper

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/14
 *   @desc   : HeaderListFooterVModel
 */
abstract class HeaderListFooterVModel<V : ViewInterface<FrameHeaderContentFooterBinding>, M : ViewModel<*>, B : ViewDataBinding>(stateViewHelper: IStateViewHelper)
    : HeaderContentFooterVModel<V, M, B>(stateViewHelper) {
    protected abstract val adapter: ViewModelAdapter
    protected abstract val recyclerView: RecyclerView

    /**
     * create RecyclerVModel
     * @return RecyclerVModel<ViewInterface<WidgetRecyclerViewBinding>>
     */
    protected open fun createRecycleVModel(): RecyclerVModel<*, *> = let {
        RecyclerVModel
                .linerLayout<WidgetRecyclerViewBinding, ViewInterface<WidgetRecyclerViewBinding>>(context,
                        LinearLayoutManager.VERTICAL, ViewGroup.LayoutParams.MATCH_PARENT)
    }


    /**
     * init Content Layout
     * @param bindingCreator Function0<B>
     */
    override fun initContent(bindingCreator: () -> B) {
        super.initContent(bindingCreator)
        initRecycleView(recyclerView)
    }


    /**
     * init RecyclerView
     * @param view RecyclerView
     */
    protected open fun initRecycleView(view: RecyclerView) {

    }


    /**
     * Toggle empty data state
     */
    protected open fun toggleEmptyView() {
        if (!isAttach()) {
            return
        }
        hideLoadingView()
        if (adapter.currentList.size > 0) {
            showContentView()
        } else {
            showEmptyView()
        }
    }

}
