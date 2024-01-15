package io.ganguo.sample.viewmodel

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.ganguo.app.helper.activity.ActivityHelper
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.databinding.WidgetRecyclerViewBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.core.viewmodel.common.widget.RecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R
import io.ganguo.sample.view.component.ComponentSampleActivity
import io.ganguo.sample.view.service.ServiceSampleActivity
import io.ganguo.sample.view.support.SupportSampleActivity
import io.ganguo.sample.view.widget.WidgetSampleActivity
import io.ganguo.utils.Apps

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/12
 *   @desc   : Demo Page
 * </pre>
 */
class ActivitySampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    /**
     * 设置标题栏
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        HeaderTitleVModel
                .sampleTitleVModel(viewIF.activity, getString(R.string.str_title))
                .let {
                    it.leftItems.get()?.firstOrNull()?.click { onBackPressed() }
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
        val viewModels = arrayListOf<ViewModel<*>>()
        viewModels.add(ItemMenuVModel.create(getString(R.string.str_service_sample)) {
            ServiceSampleActivity.start(context)
        })

        viewModels.add(ItemMenuVModel.create(getString(R.string.str_component_sample)) {
            ComponentSampleActivity.start(context)
        })

        viewModels.add(ItemMenuVModel.create(getString(R.string.str_support_sample)) {
            SupportSampleActivity.start(context)
        })

        viewModels.add(ItemMenuVModel.create(getString(R.string.str_widget_sample)) {
            WidgetSampleActivity.start(context)
        })

        adapter.addAll(viewModels)
    }

    fun onBackPressed() {
        Apps.exitByDoublePressed(allActivity = ActivityHelper.allActivity())
    }
}
