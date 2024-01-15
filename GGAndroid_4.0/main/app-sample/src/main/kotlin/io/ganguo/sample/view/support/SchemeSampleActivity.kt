package io.ganguo.sample.view.support

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.ActivitySchemeSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/03/26
 *     desc   : Scheme跳转 Demo
 * </pre>
 */
class SchemeSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivitySchemeSampleVModel>() {
    override fun createViewModel(): ActivitySchemeSampleVModel {
        return ActivitySchemeSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivitySchemeSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, SchemeSampleActivity::class.java))
        }
    }
}