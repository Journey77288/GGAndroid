package io.ganguo.viewmodel.pack.common.loading

import android.view.View
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import io.ganguo.viewmodel.R
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.databinding.IncludeMaterialProgressBarLoadMoreBinding
import io.ganguo.viewmodel.pack.base.viewmodel.BaseLoadingVModel

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/12
 *     desc   : LoadMore - ViewModel
 * </pre>
 */
class LoadingMaterialProgressVModel : BaseLoadingVModel<ViewInterface<IncludeMaterialProgressBarLoadMoreBinding>>(), RefreshFooter {
    override fun getView(): View {
        return viewInterface.binding.root
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        return 300
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
    }

    override fun setNoMoreData(noMoreData: Boolean): Boolean {
        return false
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun setPrimaryColors(vararg colors: Int) {
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
    }

    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }


    override fun onViewAttached(view: View) {
    }


    override val layoutId: Int by lazy {
        R.layout.include_material_progress_bar_load_more
    }

}