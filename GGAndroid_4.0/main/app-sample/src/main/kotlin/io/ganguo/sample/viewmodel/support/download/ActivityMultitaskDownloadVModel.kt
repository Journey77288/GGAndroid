package io.ganguo.sample.viewmodel.support.download

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
 *     time   : 2021/12/27
 *     desc   : Multitask Download Sample ViewModel
 * </pre>
 */
class ActivityMultitaskDownloadVModel :HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
    companion object {
        const val URL_FIRST = "https://static.runoob.com/images/demo/demo2.jpg"
        const val URL_SECOND = "https://static.runoob.com/images/demo/demo3.jpg"
        const val URL_THIRD = "https://static.runoob.com/images/demo/demo4.jpg"
    }

    /**
     * 配置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_multiple_download))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            addAll(
                ItemDownloadSampleVModel(URL_FIRST),
                ItemDownloadSampleVModel(URL_SECOND),
                ItemDownloadSampleVModel(URL_THIRD)
            )
        }
    }
}