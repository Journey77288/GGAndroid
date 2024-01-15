package io.ganguo.sample.view.component.scanner

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.component.scanner.ActivityCodeCreateSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/19
 *     desc   : 创建二维码 / 条形码 Demo
 * </pre>
 */
class CodeCreateSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityCodeCreateSampleVModel>() {
    override fun createViewModel(): ActivityCodeCreateSampleVModel {
        return ActivityCodeCreateSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityCodeCreateSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, CodeCreateSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}