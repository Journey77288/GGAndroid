package io.ganguo.sample.viewmodel.service.open

import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.view.service.open.OauthSampleActivity
import io.ganguo.sample.view.service.open.ShareSampleActivity
import io.ganguo.sample.viewmodel.ItemMenuVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/19
 *   @desc   : Open Sdk Sample
 * </pre>
 */
class ActivityOpenSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    /**
     * 设置标题栏
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        HeaderTitleVModel
                .sampleTitleVModel(viewIF.activity, "Open Sdk Sample")
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        adapter.addAll(
            listOf(
                ItemMenuVModel.create("OAuth Sdk Sample") {
                    OauthSampleActivity.start(context)
                },
                ItemMenuVModel.create("Share Sdk Sample") {
                    ShareSampleActivity.start(context)
                }
            )
        )
    }
}
