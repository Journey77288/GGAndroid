package io.ganguo.sample.viewmodel.widget.switch_button

import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.core.viewmodel.common.widget.TextViewModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/12
 *   @desc   : Switch Button Sample
 * </pre>
 */
class ActivitySwitchIOSButtonSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
    /**
     * 设置标题栏
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        HeaderTitleVModel
                .sampleTitleVModel(viewIF.activity, "Switch Button Sample")
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }


    override fun onViewAttached(view: View) {
        adapter.addAll(
            listOf(
                TextViewModel.sampleExplainVModel("iOS Style"),
                ItemSwitchIOSButtonVModel()
            )
        )
        toggleEmptyView()
    }
}
