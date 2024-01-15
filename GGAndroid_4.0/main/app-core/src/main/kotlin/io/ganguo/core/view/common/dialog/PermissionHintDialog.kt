package io.ganguo.core.view.common.dialog

import android.content.Context
import android.view.ViewGroup
import io.ganguo.resources.R
import io.ganguo.core.databinding.ComponentPermissionHintDialogBinding
import io.ganguo.core.viewmodel.common.component.DialogPermissionHintVModel
import io.ganguo.mvvm.view.ViewModelDialog

/**
 * <pre>
 *     author : lucas
 *     time   : 2023/04/13
 *     desc   : 权限作用提示弹窗
 * </pre>
 */
class PermissionHintDialog(context: Context, private val title: String, private val content: String) :
    ViewModelDialog<ComponentPermissionHintDialogBinding, DialogPermissionHintVModel>(context, R.style.Dialog_Permission) {
    override fun createViewModel(): DialogPermissionHintVModel {
        return DialogPermissionHintVModel(title, content)
    }

    override val windowWidth: Int = ViewGroup.LayoutParams.MATCH_PARENT
    override val windowHeight: Int = ViewGroup.LayoutParams.MATCH_PARENT

    override fun onViewAttached(viewModel: DialogPermissionHintVModel) {

    }
}