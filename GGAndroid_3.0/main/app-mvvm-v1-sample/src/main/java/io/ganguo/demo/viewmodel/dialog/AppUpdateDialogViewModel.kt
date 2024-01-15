package io.ganguo.demo.viewmodel.dialog

import android.view.View
import io.ganguo.app.update.model.AppVersionModel
import io.ganguo.demo.R
import io.ganguo.demo.databinding.DialogAppUpdateBinding
import io.ganguo.viewmodel.core.viewinterface.DialogInterface
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2020/05/29
 *     desc   :
 * </pre>
 */
class AppUpdateDialogViewModel(var model: AppVersionModel,
                               private var cancelCallback: () -> Unit,
                               private var updateCallback: () -> Unit) : BaseViewModel<DialogInterface<DialogAppUpdateBinding>>() {

    override val layoutId: Int by lazy {
        R.layout.dialog_app_update
    }

    override fun onViewAttached(viewInterface: View) {

    }

    fun actionCancel() {
        cancelCallback.invoke()
        viewInterface.dialog.dismiss()
    }

    fun actionUpdate() {
        updateCallback.invoke()
        viewInterface.dialog.dismiss()
    }

}