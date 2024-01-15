package io.ganguo.sample.view.service

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.service.ActivityLocaleSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/07/13
 *     desc   : 语言切换 Demo
 * </pre>
 */
class LocaleSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityLocaleSampleVModel>() {
    override fun createViewModel(): ActivityLocaleSampleVModel {
        return ActivityLocaleSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityLocaleSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, LocaleSampleActivity::class.java))
        }
    }
}