package io.ganguo.sample.viewmodel.service

import android.util.Log
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
import io.ganguo.sample.R
import io.ganguo.sample.view.service.PaySampleActivity
import io.ganguo.sample.view.service.http.RetrofitHttpSampleActivity
import io.ganguo.sample.view.service.image.ImageEngineSampleActivity
import io.ganguo.sample.view.service.open.OpenSampleActivity
import io.ganguo.sample.viewmodel.ItemMenuVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/12/23
 *   @desc   : Service Sample
 * </pre>
 */
class ActivityServiceSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    /**
     * 设置标题栏
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        HeaderTitleVModel
            .sampleTitleVModel(viewIF.activity, "Service Sample")
            .let {
                ViewModelHelper.bind(header.invoke(), this, it)
            }
    }


    /**
     * create RecyclerVModel and bind to ViewBinding
     * @return RecyclerVModel<*, *>
     */
    override fun createRecycleVModel(): RecyclerVModel<*, *> = let {
        RecyclerVModel.gridLayout<WidgetRecyclerViewBinding, ViewInterface<WidgetRecyclerViewBinding>>(
            context, 3, LinearLayoutManager.VERTICAL, 0
        )
    }

    override fun onViewAttached(view: View) {
        adapter.addAll(
            listOf(
                ItemMenuVModel.create(getString(R.string.str_image_sample)) {
                    ImageEngineSampleActivity.start(context)
                },
                ItemMenuVModel.create(getString(R.string.str_http_sample)) {
                    RetrofitHttpSampleActivity.start(context)
                },
                ItemMenuVModel.create(getString(R.string.str_open_sdk_sample)) {
                    OpenSampleActivity.start(context)
                },
                ItemMenuVModel.create(getString(R.string.str_pay_sample)) {
                    PaySampleActivity.start(context)
                }
            )
        )
    }

}
