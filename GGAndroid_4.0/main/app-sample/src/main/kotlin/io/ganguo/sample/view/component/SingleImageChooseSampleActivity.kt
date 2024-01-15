package io.ganguo.sample.view.component

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.component.ActivitySingleImageChooseSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/19
 *     desc   : Single Image Choose Sample
 * </pre>
 */
class SingleImageChooseSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivitySingleImageChooseSampleVModel>() {
    override fun createViewModel(): ActivitySingleImageChooseSampleVModel {
        return ActivitySingleImageChooseSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivitySingleImageChooseSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SingleImageChooseSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}