package io.ganguo.sample.viewmodel.support.rxbus

import android.view.View
import androidx.databinding.ObservableField
import io.ganguo.core.viewmodel.BaseViewModel
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.rxbus.RxBus
import io.ganguo.sample.R
import io.ganguo.sample.bean.EVENT_MESSAGE_UPDATE
import io.ganguo.sample.databinding.ItemRxbusSenderBinding
import io.ganguo.sample.view.support.rxbus.RxBusStickyObserverSampleActivity

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/09
 *     desc   : RxBus Sender ItemViewModel
 * </pre>
 */
class ItemRxBusSenderVModel : BaseViewModel<ViewInterface<ItemRxbusSenderBinding>>() {
    override val layoutId: Int = R.layout.item_rxbus_sender
    val input = ObservableField<String>()
    val stickyInput = ObservableField<String>()

    override fun onViewAttached(view: View) {

    }

    /**
     * click send button
     *
     * @param view
     */
    fun actionSendMessage(view: View) {
        RxBus.get().sendEvent(EVENT_MESSAGE_UPDATE, input.get().orEmpty())
    }

    /**
     * click send sticky event button
     */
    fun actionSendStickyMessage(view: View) {
        RxBus.get().sendStickyEvent(EVENT_MESSAGE_UPDATE, stickyInput.get().orEmpty())
        RxBusStickyObserverSampleActivity.start(context)
    }
}