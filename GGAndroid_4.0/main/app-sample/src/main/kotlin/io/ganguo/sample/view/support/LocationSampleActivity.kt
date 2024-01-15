package io.ganguo.sample.view.support

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.location.ActivityLocationSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/06/04
 *     desc   : Location Sample
 * </pre>
 */
class LocationSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityLocationSampleVModel>() {
    override fun createViewModel(): ActivityLocationSampleVModel {
        return ActivityLocationSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityLocationSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, LocationSampleActivity::class.java))
        }
    }
}