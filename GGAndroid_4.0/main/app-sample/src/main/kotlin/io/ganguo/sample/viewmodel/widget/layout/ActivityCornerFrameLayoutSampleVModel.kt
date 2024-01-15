package io.ganguo.sample.viewmodel.widget.layout

import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/13
 *   @desc   : 圆角布局 Demo
 * </pre>
 */
class ActivityCornerFrameLayoutSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {


    /**
     * 设置标题栏
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        HeaderTitleVModel
                .sampleTitleVModel(viewIF.activity, "CornerFrameLayout Sample")
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }


    override fun onViewAttached(view: View) {
        adapter.addAll(listOf(ItemCornerFrameLayoutVModel()))
        toggleEmptyView()
    }
}
