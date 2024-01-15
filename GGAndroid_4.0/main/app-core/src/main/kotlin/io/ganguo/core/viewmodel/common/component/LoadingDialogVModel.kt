package io.ganguo.core.viewmodel.common.component

import android.view.View
import androidx.databinding.ObservableField
import io.ganguo.core.R
import io.ganguo.core.databinding.ComponentLoadingDialogBinding
import io.ganguo.mvvm.viewinterface.DialogInterface
import io.ganguo.mvvm.viewmodel.ViewModel

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/15
 *   @desc   : LoadingDialogVModel
 * </pre>
 */
class LoadingDialogVModel : ViewModel<DialogInterface<ComponentLoadingDialogBinding>>() {
    val hint = ObservableField<String>()
    override val layoutId: Int by lazy {
        R.layout.component_loading_dialog
    }

    override fun onViewAttached(view: View) {
        viewIF.dialog.setCancelMessage(null)
    }

    /**
     * update dialog hint
     * @param hint String
     */
    fun updateHint(hint: String) {
        this.hint.set(hint)
    }

}
