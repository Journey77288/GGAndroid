package io.ganguo.sample.viewmodel.widget

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.databinding.WidgetRecyclerViewBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.core.viewmodel.common.widget.RecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.view.widget.CorerFrameLayoutSampleActivity
import io.ganguo.sample.view.widget.SwitchIOSButtonSampleActivity
import io.ganguo.sample.viewmodel.ItemMenuVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/12
 *   @desc   : Widget Sample
 * </pre>
 */
class ActivityWidgetSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {


    /**
     * 设置标题栏
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        HeaderTitleVModel
                .sampleTitleVModel(viewIF.activity, "Widget Sample")
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }


    /**
     * create RecyclerVModel and bind to ViewBinding
     * @return RecyclerVModel<*, *>
     */
    override fun createRecycleVModel(): RecyclerVModel<*, *> = let {
        RecyclerVModel
                .gridLayout<WidgetRecyclerViewBinding, ViewInterface<WidgetRecyclerViewBinding>>(context, 3, LinearLayoutManager.VERTICAL, 0)
    }

    override fun onViewAttached(view: View) {
        adapter.addAll(
            listOf(
                ItemMenuVModel.create("Switch Button Sample") {
                    SwitchIOSButtonSampleActivity.start(context)
                },
                ItemMenuVModel.create("CornerFrameLayout Sample") {
                    CorerFrameLayoutSampleActivity.start(context)
                }
            )
        )
    }


}
