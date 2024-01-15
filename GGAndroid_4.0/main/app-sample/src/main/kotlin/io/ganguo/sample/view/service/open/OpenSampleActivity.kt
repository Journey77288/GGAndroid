package io.ganguo.sample.view.service.open

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.service.open.ActivityOpenSampleVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/19
 *   @desc   : Open Sdk Sample
 * </pre>
 */
class OpenSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityOpenSampleVModel>() {

    override fun createViewModel(): ActivityOpenSampleVModel {
        return ActivityOpenSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityOpenSampleVModel) {
    }



    companion object {

        /**
         * start OauthSampleActivity
         * @param context Context
         */
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, OpenSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}
