package io.ganguo.sample.viewmodel.component.scanner

import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R
import io.ganguo.sample.view.component.scanner.CodeCreateSampleActivity
import io.ganguo.sample.view.component.scanner.ScannerSampleActivity
import io.ganguo.sample.viewmodel.ItemMenuVModel
import io.ganguo.scanner.bean.CodecType

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/09
 *     desc   : Code Scanner Sample
 * </pre>
 */
class ActivityCodeScannerSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    /**
     * 配置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_code_scanner_sample))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            val viewModels = arrayListOf<ViewModel<*>>()
            viewModels.add(ItemMenuVModel.create(getString(R.string.str_scan_qr_code)) {
                ScannerSampleActivity.start(context, CodecType.QR.value, true)
            })
            viewModels.add(ItemMenuVModel.create(getString(R.string.str_scan_qr_code_without_frame)) {
                ScannerSampleActivity.start(context, CodecType.QR.value, false)
            })
            viewModels.add(ItemMenuVModel.create(getString(R.string.str_scan_bar_code)) {
                ScannerSampleActivity.start(context, CodecType.BAR.value, true)
            })
            viewModels.add(ItemMenuVModel.create(getString(R.string.str_create_code)) {
                CodeCreateSampleActivity.start(context)
            })
            addAll(viewModels)
        }
    }
}