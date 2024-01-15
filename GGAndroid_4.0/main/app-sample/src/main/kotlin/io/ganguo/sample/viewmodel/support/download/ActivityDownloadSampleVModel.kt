package io.ganguo.sample.viewmodel.support.download

import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R
import io.ganguo.sample.view.support.download.MultitaskDownloadActivity
import io.ganguo.sample.view.support.download.NotificationDownloadActivity
import io.ganguo.sample.view.support.download.SingleTaskDownloadActivity
import io.ganguo.sample.viewmodel.ItemMenuVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/11/29
 *     desc   : Download Sample ViewModel
 * </pre>
 */
class ActivityDownloadSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    /**
     * 配置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_download_sample))
            .let {
                ViewModelHelper.bind(header.invoke(), this, it)
            }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            val viewModels = arrayListOf<ViewModel<*>>()
            viewModels.add(ItemMenuVModel.create(getString(R.string.str_single_download)) {
                SingleTaskDownloadActivity.start(context)
            })
            viewModels.add(ItemMenuVModel.create(getString(R.string.str_multiple_download)) {
                MultitaskDownloadActivity.start(context)
            })
            viewModels.add(ItemMenuVModel.create(getString(R.string.str_download_with_notification)) {
                NotificationDownloadActivity.start(context)
            })
            addAll(viewModels)
        }
    }
}