package io.ganguo.demo.view.dialog

import android.content.Context
import android.view.ViewGroup
import io.ganguo.app.update.model.AppVersionModel
import io.ganguo.demo.databinding.DialogAppUpdateBinding
import io.ganguo.demo.viewmodel.dialog.AppUpdateDialogViewModel
import io.ganguo.viewmodel.core.view.ViewModelDialog

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2020/05/29
 *     desc   :
 * </pre>
 */

class AppUpdateDialog(context: Context,
                      var model: AppVersionModel,
                      var cancelCallback: () -> Unit,
                      var updateCallback: () -> Unit) : ViewModelDialog<DialogAppUpdateBinding, AppUpdateDialogViewModel>(context) {

    override fun createViewModel(): AppUpdateDialogViewModel {
        return AppUpdateDialogViewModel(model, cancelCallback, updateCallback)
    }


    override val dialogWidth: Int
        get() = ViewGroup.LayoutParams.MATCH_PARENT
    override val dialogHeight: Int
        get() = ViewGroup.LayoutParams.MATCH_PARENT


    override fun onViewAttached(viewModel: AppUpdateDialogViewModel) {
    }

}