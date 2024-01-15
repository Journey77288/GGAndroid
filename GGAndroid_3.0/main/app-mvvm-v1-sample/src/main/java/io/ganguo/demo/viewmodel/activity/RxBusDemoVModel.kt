package io.ganguo.demo.viewmodel.activity

import android.view.View
import androidx.databinding.ObservableField
import io.ganguo.demo.R
import io.ganguo.demo.bean.ConstantsEvent
import io.ganguo.demo.databinding.ActivityRxbusDemoBinding
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.rx.bus.RxBus
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.helper.keyboard.hideKeyboard
import io.ganguo.utils.util.Strings
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * RxBus Demo
 * Created by leo on 2018/8/15.
 */
class RxBusDemoVModel : BaseViewModel<ActivityInterface<ActivityRxbusDemoBinding>>() {
    override val layoutId: Int by lazy {
        R.layout.activity_rxbus_demo
    }
    var input = ObservableField<String>()

    override fun onViewAttached(view: View) {}


    /**
     * send RxBus click
     * @return
     */
    fun actionSendClick(): View.OnClickListener {
        return View.OnClickListener {
            if (Strings.isEmpty(input.get())) {
                ToastHelper.showMessage("请输入要发送的内容！")
                return@OnClickListener
            }
            // TODO: 2018/8/15 在这里发送需要传递的内容/Object，在需要接收到的地方接收即可，Demo在MainVModel中接收
            RxBus.getDefault().send(input.get(), ConstantsEvent.BUS_DEMO_KEY)
            rootView?.let {
                hideKeyboard(it)
            }
            viewInterface.activity.finish()
        }
    }


}
