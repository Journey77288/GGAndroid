package io.ganguo.sample.viewmodel.support.rxbus

import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.databinding.ObservableField
import com.binaryfork.spanny.Spanny
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ItemRxbusObserverBinding
import io.ganguo.sample.view.support.rxbus.RxBusSenderSampleActivity

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/09
 *     desc   : RxBus Observer ItemViewModel
 * </pre>
 */
class ItemRxBusObserverVModel(val buttonVisible: Boolean = true) : ViewModel<ViewInterface<ItemRxbusObserverBinding>>() {
    override val layoutId: Int = R.layout.item_rxbus_observer
    val message = ObservableField<Spanny>()

    init {
        message.set(Spanny(getString(R.string.str_rxbus_observer_result)))
    }

    override fun onViewAttached(view: View) {

    }

    /**
     * Click send message button
     *
     * @param view
     */
    fun actionEnterSendMessagePage(view: View) {
        RxBusSenderSampleActivity.start(context)
    }

    /**
     * update message
     *
     * @param content
     */
    fun updateMessage(content: String) {
        message.set(Spanny(getString(R.string.str_rxbus_observer_result))
                .append(" ")
                .append(content, ForegroundColorSpan(getColor(io.ganguo.resources.R.color.colorPrimary))))
    }
}