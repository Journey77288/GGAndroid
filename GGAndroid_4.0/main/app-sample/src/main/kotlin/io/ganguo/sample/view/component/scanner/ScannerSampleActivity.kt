package io.ganguo.sample.view.component.scanner

import android.content.Context
import android.content.Intent
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.bean.Keys
import io.ganguo.sample.databinding.ActivityScannerSampleBinding
import io.ganguo.sample.viewmodel.component.scanner.ActivityScannerSampleVModel
import io.ganguo.scanner.bean.CodecType

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/09
 *     desc   : Scanner Sample
 * </pre>
 */
class ScannerSampleActivity : AppCoreVModelActivity<ActivityScannerSampleBinding, ActivityScannerSampleVModel>() {
    override fun createViewModel(): ActivityScannerSampleVModel {
        val scanType = intent.getIntExtra(Keys.Intent.Common.DATA, CodecType.QR.value)
        val supportScanningFrame = intent.getBooleanExtra(Keys.Intent.Common.FLAG, true)
        return ActivityScannerSampleVModel(scanType, supportScanningFrame)
    }

    override fun onViewAttached(viewModel: ActivityScannerSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context, scanType: Int, supportScanningFrame: Boolean) {
            val intent = Intent(context, ScannerSampleActivity::class.java)
            intent.putExtra(Keys.Intent.Common.DATA, scanType)
            intent.putExtra(Keys.Intent.Common.FLAG, supportScanningFrame)
            context.startActivity(intent)
        }
    }
}