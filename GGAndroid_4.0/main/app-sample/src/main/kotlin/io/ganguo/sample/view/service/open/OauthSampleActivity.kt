package io.ganguo.sample.view.service.open

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.factory.GGFactory
import io.ganguo.sample.viewmodel.service.open.ActivityOauthSampleVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/15
 *   @desc   : Third-party login / Obtain account information
 * </pre>
 */
class OauthSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityOauthSampleVModel>() {

    override fun createViewModel(): ActivityOauthSampleVModel {
        return ActivityOauthSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityOauthSampleVModel) {
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
            val intent = Intent(context, OauthSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}
