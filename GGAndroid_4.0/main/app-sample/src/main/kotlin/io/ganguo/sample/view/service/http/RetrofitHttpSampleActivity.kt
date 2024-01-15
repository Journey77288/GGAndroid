package io.ganguo.sample.view.service.http

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.service.http.ActivityRetrofitHttpSampleVModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/13
 *   @desc   :Retrofit Http Sample
 * </pre>
 */
class RetrofitHttpSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityRetrofitHttpSampleVModel>() {
    override fun createViewModel(): ActivityRetrofitHttpSampleVModel {
        return ActivityRetrofitHttpSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityRetrofitHttpSampleVModel) {
    }


    companion object {


        /**
         * start RetrofitHttpSampleActivity
         * @param context Context
         */
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, RetrofitHttpSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}
