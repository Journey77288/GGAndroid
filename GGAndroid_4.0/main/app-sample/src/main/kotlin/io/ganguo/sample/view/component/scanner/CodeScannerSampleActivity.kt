package io.ganguo.sample.view.component.scanner

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.component.scanner.ActivityCodeScannerSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/09
 *     desc   : Code Scanner Sample
 * </pre>
 */
class CodeScannerSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityCodeScannerSampleVModel>() {
    override fun createViewModel(): ActivityCodeScannerSampleVModel {
        return ActivityCodeScannerSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityCodeScannerSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, CodeScannerSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}