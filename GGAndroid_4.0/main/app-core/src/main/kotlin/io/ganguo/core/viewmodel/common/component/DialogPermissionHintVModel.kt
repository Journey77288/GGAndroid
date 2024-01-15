package io.ganguo.core.viewmodel.common.component

import android.view.View
import io.ganguo.core.R
import io.ganguo.core.databinding.ComponentPermissionHintDialogBinding
import io.ganguo.mvvm.viewinterface.DialogInterface
import io.ganguo.mvvm.viewmodel.ViewModel

/**
 * <pre>
 *     author : lucas
 *     time   : 2023/04/11
 *     desc   : 权限作用提示弹窗 ViewModel
 * </pre>
 */
class DialogPermissionHintVModel(val title: String, val content: String): ViewModel<DialogInterface<ComponentPermissionHintDialogBinding>>() {
    override val layoutId: Int = R.layout.component_permission_hint_dialog

    override fun onViewAttached(view: View) {

    }
}