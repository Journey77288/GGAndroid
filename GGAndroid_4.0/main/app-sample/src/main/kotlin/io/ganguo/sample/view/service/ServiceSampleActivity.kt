package io.ganguo.sample.view.service

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.service.ActivityServiceSampleVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/12/23
 *   @desc   : Service Sample
 * </pre>
 */
class ServiceSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityServiceSampleVModel>() {
    override fun createViewModel(): ActivityServiceSampleVModel {
        return ActivityServiceSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityServiceSampleVModel) {

    }


    companion object {


        /**
         * start ServiceSampleActivity
         * @param context Context
         */
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, ServiceSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}
