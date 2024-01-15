package io.scanner.sample

import android.content.Context
import android.content.Intent
import io.ganguo.scanner.bean.CodecType
import io.ganguo.viewmodel.core.view.ViewModelActivity
import io.scanner.sample.databinding.ActivityScannerBinding
import io.scanner.sample.viewmodel.ScannerSampleVModel

/**
 *
 *
 * 扫描二维码/条形码 示例Demo
 *
 * Created by leo on 2018/8/8.
 */
class ScannerSampleActivity : ViewModelActivity<ActivityScannerBinding, ScannerSampleVModel>() {

    override fun createViewModel(): ScannerSampleVModel {
        val type = intent.getIntExtra(KEY_DATA, CodecType.ALL.value)
        val supportScanningFrame = intent.getBooleanExtra(KEY_SUPPORT_FRAME, true)
        return ScannerSampleVModel(type, supportScanningFrame)
    }

    override fun onViewAttached(viewModel: ScannerSampleVModel?) {
    }

    companion object {
        private const val KEY_DATA = "data"
        private const val KEY_SUPPORT_FRAME = "mode"

        /**
         * function：create Intent
         *
         * @param context
         * @param type
         * @return
         */
        fun intentFor(context: Context, type: Int, supportScanningFrame: Boolean?): Intent {
            val intent = Intent(context, ScannerSampleActivity::class.java)
            intent.putExtra(KEY_DATA, type)
            intent.putExtra(KEY_SUPPORT_FRAME, supportScanningFrame)
            return intent
        }
    }

}
