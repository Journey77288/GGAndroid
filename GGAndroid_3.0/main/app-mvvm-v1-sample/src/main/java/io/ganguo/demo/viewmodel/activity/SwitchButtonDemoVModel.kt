package io.ganguo.demo.viewmodel.activity

import android.view.View
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivitySwitchButtonBinding
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.switchs.OnSwitchChangeListener
import io.ganguo.switchs.SwitchButton
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/09
 *     desc   : SwitchButton Demo ViewModel
 * </pre>
 */
class SwitchButtonDemoVModel : BaseViewModel<ActivityInterface<ActivitySwitchButtonBinding>>(), OnSwitchChangeListener {
    override val layoutId: Int by lazy {
        R.layout.activity_switch_button
    }

    override fun onViewAttached(view: View) {
        viewInterface.binding.swIos1.setToggleChangeListener(this)
        viewInterface.binding.swIos2.setToggleChangeListener(this)
    }


    override fun onSwitchToggleChange(isOpen: Boolean, button: SwitchButton) {
        if (isOpen) {
            ToastHelper.showMessage("已打开")
        } else {
            ToastHelper.showMessage("已关闭")
        }
    }
}