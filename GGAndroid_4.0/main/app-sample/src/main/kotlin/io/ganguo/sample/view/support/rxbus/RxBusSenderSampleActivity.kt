package io.ganguo.sample.view.support.rxbus

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.rxbus.ActivityRxBusSenderSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/09
 *     desc   : RxBus Sender Sample
 * </pre>
 */
class RxBusSenderSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityRxBusSenderSampleVModel>() {
    override fun createViewModel(): ActivityRxBusSenderSampleVModel {
        return ActivityRxBusSenderSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityRxBusSenderSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, RxBusSenderSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}