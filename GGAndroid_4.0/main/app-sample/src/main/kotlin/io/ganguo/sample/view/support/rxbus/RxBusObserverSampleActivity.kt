package io.ganguo.sample.view.support.rxbus

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.rxbus.ActivityRxBusObserverSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/09
 *     desc   : RxBus Observer Sample
 * </pre>
 */
class RxBusObserverSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityRxBusObserverSampleVModel>() {
    override fun createViewModel(): ActivityRxBusObserverSampleVModel {
        return ActivityRxBusObserverSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityRxBusObserverSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, RxBusObserverSampleActivity::class.java)
            context.startActivity(intent)
        }
    }
}