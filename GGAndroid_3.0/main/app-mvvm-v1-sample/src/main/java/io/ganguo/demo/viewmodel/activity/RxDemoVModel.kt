package io.ganguo.demo.viewmodel.activity

import android.view.View
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivityRxDemoBinding
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.log.core.Logger
import io.ganguo.rx.RxActions
import io.ganguo.rx.RxCommand
import io.ganguo.rx.RxProperty
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.util.Numbers
import io.ganguo.utils.util.Strings
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.pack.RxVMLifecycle
import io.reactivex.functions.Consumer

/**
 * Created by Roger on 7/26/16.
 */
class RxDemoVModel : BaseViewModel<ActivityInterface<ActivityRxDemoBinding>>() {
    override val layoutId: Int by lazy {
        R.layout.activity_rx_demo
    }
    val input = RxProperty("1")

    var clickCommand: RxCommand<View.OnClickListener>? = null
        private set

    /**
     * Init RxProperty and RxCommand in constructor,
     * otherwise RxProperty will subscribe multi-times when viewModel attach multi-times.
     */
    init {
        // subscribe text change
        subscribeInput()
        onClickCommand()
    }

    /**
     * function: lazy init clickCommand
     */
    protected fun onClickCommand() {
        clickCommand = RxCommand(input.map { s -> Strings.isNotEmpty(s) && s.length < 4 }, View.OnClickListener { ToastHelper.showMessage("Submit quantity") })
    }

    /**
     * function: 订阅输入事件
     */
    private fun subscribeInput() {
        input.compose(RxVMLifecycle.doWhenAttach(this))
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Consumer { s ->
                    Logger.d("call: " + "input onChange1: " + s)
                    if (Strings.isEmpty(s) || Numbers.toInt(s) == 0) {
                        input.set("1")
                        return@Consumer
                    }
                    // 限制个位数前输入数字0
                    if (s.length > 1 && s.toCharArray()[0] == '0') {
                        input.set(s.substring(1, s.length))
                        return@Consumer
                    }

                }, RxActions.printThrowable())
    }


    override fun onViewAttached(view: View) {

    }
}
