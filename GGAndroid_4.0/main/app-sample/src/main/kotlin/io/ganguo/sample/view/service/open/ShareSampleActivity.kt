package io.ganguo.sample.view.service.open

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.factory.GGFactory
import io.ganguo.sample.viewmodel.service.open.ActivityShareSampleVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/19
 *   @desc   :  Third-party Share
 * </pre>
 */
class ShareSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityShareSampleVModel>() {
    override fun createViewModel(): ActivityShareSampleVModel {
        return ActivityShareSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityShareSampleVModel) {
    }

    /**
     *  Must be registered GGFactory.registerActivityResult(requestCode, resultCode, data)
     * @param requestCode Int
     * @param resultCode Int
     * @param data Intent?
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        GGFactory.registerActivityResult(requestCode, resultCode, data, this)
    }

    companion object {

        /**
         * start OauthSampleActivity
         * @param context Context
         */
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, ShareSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}
