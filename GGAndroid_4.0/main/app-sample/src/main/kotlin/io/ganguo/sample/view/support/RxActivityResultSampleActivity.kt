package io.ganguo.sample.view.support

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.ActivityRxActivityResultSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : RxActivityResult Sample
 * </pre>
 */
class RxActivityResultSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityRxActivityResultSampleVModel>() {
    override fun createViewModel(): ActivityRxActivityResultSampleVModel {
        return ActivityRxActivityResultSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityRxActivityResultSampleVModel) {

    }

    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, RxActivityResultSampleActivity::class.java)
        }
    }
}