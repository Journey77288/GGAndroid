package io.ganguo.demo.viewmodel.activity

import android.view.View
import androidx.databinding.ObservableField
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivityKeyBoardStateDemoBinding
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.helper.keyboard.KeyboardStateListener
import io.ganguo.utils.helper.keyboard.addKeyboardListener
import io.ganguo.viewmodel.pack.base.viewmodel.BaseActivityVModel

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/27
 *     desc   : KeyboardHelper Demo
 * </pre>
 */
class KeyboardStateDemoVModel : BaseActivityVModel<ActivityKeyBoardStateDemoBinding>(), KeyboardStateListener {
    override val layoutId: Int by lazy {
        R.layout.activity_key_board_state_demo
    }
    var keyboardState = ObservableField<String>()

    override fun onViewAttached(view: View) {
        addKeyboardListener(viewInterface.activity, this)
    }

    /**
     * 键盘打开
     * @param keyboardHeight Int
     */
    override fun onKeyboardShow(keyboardHeight: Int) {
        keyboardState.set("键盘打开${keyboardHeight}")
        ToastHelper.showMessage("键盘打开")
    }

    /**
     * 键盘关闭
     * @param keyboardHeight Int
     */
    override fun onKeyboardHide(keyboardHeight: Int) {
        keyboardState.set("键盘关闭${keyboardHeight}")
        ToastHelper.showMessage("键盘关闭")
    }


}