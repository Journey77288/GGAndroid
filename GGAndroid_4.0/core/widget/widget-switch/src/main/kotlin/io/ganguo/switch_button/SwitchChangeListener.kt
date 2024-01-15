package io.ganguo.switch_button

import io.ganguo.switch_button.SwitchButton


/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/05
 *     desc   : SwitchButton状态切换回调
 * </pre>
 */
interface SwitchChangeListener {
    fun onSwitchToggleChange(isOpen: Boolean, button: SwitchButton)
}
