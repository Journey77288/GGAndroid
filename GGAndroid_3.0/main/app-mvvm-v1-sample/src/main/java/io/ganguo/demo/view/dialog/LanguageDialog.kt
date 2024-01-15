package io.ganguo.demo.view.dialog

import android.content.Context
import io.ganguo.core.R
import io.ganguo.demo.viewmodel.dialog.LanguageDialogVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.databinding.DialogGgBinding
import io.ganguo.viewmodel.pack.ui.dialog.BottomVModelDialog

/**
 * <pre>
 *     author : leo
 *     time   : 2020/03/12
 *     desc   : 语言切换弹窗
 * </pre>
 */
class LanguageDialog(context: Context, theme: Int= R.style.Dialog_Immersion) : BottomVModelDialog<DialogGgBinding, LanguageDialogVModel>(context,theme) {
    override fun createViewModel(): LanguageDialogVModel {
        return LanguageDialogVModel()
    }

    override fun onViewAttached(viewModel: LanguageDialogVModel) {
    }
}