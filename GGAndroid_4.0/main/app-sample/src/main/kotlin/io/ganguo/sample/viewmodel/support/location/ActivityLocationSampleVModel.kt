package io.ganguo.sample.viewmodel.support.location

import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/06/04
 *     desc   : Location Sample ViewModel
 * </pre>
 */
class ActivityLocationSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    /**
     * 配置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_location_sample))
            .let {
                ViewModelHelper.bind(header.invoke(), this, it)
            }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            add(ItemLocationSampleVModel())
        }
    }
}