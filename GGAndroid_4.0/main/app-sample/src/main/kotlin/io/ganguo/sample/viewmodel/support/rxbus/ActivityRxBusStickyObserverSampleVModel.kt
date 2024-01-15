package io.ganguo.sample.viewmodel.support.rxbus

import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.rxbus.RxBus
import io.ganguo.rxjava.printThrowable
import io.ganguo.sample.R
import io.ganguo.sample.bean.EVENT_MESSAGE_UPDATE
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.kotlin.addTo

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/12/29
 *     desc   : RxBus Sticky Observer Sample ViewModel
 * </pre>
 */
class ActivityRxBusStickyObserverSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
    private val observerVModel: ItemRxBusObserverVModel by lazy { ItemRxBusObserverVModel(false) }

    init {
        initStickyObserver()
    }

    @MainThread
    private fun initStickyObserver() {
        RxBus.get().receiveStickyEvent(EVENT_MESSAGE_UPDATE, String::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { observerVModel.updateMessage(it) }
                .subscribe(Functions.emptyConsumer(), printThrowable("--initStickyObserver--"))
                .addTo(lifecycleComposite)
    }

    /**
     * 配置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_rxbus_sticky_observer_sample))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            add(observerVModel)
        }
    }
}