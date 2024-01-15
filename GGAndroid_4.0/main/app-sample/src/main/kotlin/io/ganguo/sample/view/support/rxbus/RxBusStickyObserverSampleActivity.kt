package io.ganguo.sample.view.support.rxbus

import android.content.Context
import android.content.Intent
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.view.common.AppCoreVModelActivity
import io.ganguo.sample.viewmodel.support.rxbus.ActivityRxBusStickyObserverSampleVModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/12/29
 *     desc   : RxBus Sticky Observer Sample
 * </pre>
 */
class RxBusStickyObserverSampleActivity : AppCoreVModelActivity<FrameHeaderContentFooterBinding, ActivityRxBusStickyObserverSampleVModel>() {
    override fun createViewModel(): ActivityRxBusStickyObserverSampleVModel {
        return ActivityRxBusStickyObserverSampleVModel()
    }

    override fun onViewAttached(viewModel: ActivityRxBusStickyObserverSampleVModel) {

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, RxBusStickyObserverSampleActivity::class.java))
        }
    }
}