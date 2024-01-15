package io.ganguo.sample.viewmodel.widget.switch_button

import android.view.View
import io.ganguo.appcompat.Toasts
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ItemSwitchButtonIosBinding
import io.ganguo.switch_button.SwitchButton
import io.ganguo.switch_button.SwitchChangeListener

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/12
 *   @desc   : iOS Style Switch
 * </pre>
 */
class ItemSwitchIOSButtonVModel : ViewModel<ViewInterface<ItemSwitchButtonIosBinding>>(), SwitchChangeListener {
    override val layoutId: Int = R.layout.item_switch_button_ios
    override fun onViewAttached(view: View) {
        viewIF.binding.swIos1.setSwitchChangeListener(this)
        viewIF.binding.swIos2.setSwitchChangeListener(this)
    }

    /**
     * switch result callback
     * @param isOpen Boolean
     * @param button SwitchButton
     */
    override fun onSwitchToggleChange(isOpen: Boolean, button: SwitchButton) {
        Toasts.show("$isOpen")
    }

}
