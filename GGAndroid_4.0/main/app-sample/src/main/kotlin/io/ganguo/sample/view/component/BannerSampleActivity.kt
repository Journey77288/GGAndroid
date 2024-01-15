package io.ganguo.sample.view.component

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.component.ActivityBannerSampleVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/05
 *   @desc   : Banner Sample
 * </pre>
 */
class BannerSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityBannerSampleVModel>() {
    override fun createViewModel(): ActivityBannerSampleVModel {
        return ActivityBannerSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityBannerSampleVModel) {
    }


    companion object {

        /**
         * start BannerSampleActivity
         * @param context Context
         */
        @Override
        fun start(context: Context) {
            val intent = Intent(context, BannerSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}
