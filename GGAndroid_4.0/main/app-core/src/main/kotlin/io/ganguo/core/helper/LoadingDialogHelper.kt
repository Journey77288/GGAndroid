package io.ganguo.core.helper

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import io.ganguo.core.view.common.dialog.LoadingDialog
import io.ganguo.resources.ResourcesHelper

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/15
 *   @desc   :LoadingDialog Helper
 * </pre>
 */
object LoadingDialogHelper {
    private var dialog: LoadingDialog? = null

    /**
     * show MaterLoading，Support for life cycle binding
     * @param context Context
     * @param msgId Int
     */
    fun showMaterLoading(context: Context, @StringRes msgId: Int) {
        showMaterLoading(context, ResourcesHelper.getString(msgId))
    }

    /**
     * show MaterLoading，Support for life cycle binding
     * @param context Context
     * @param message String
     */
    fun showMaterLoading(context: Context, message: String) {
        if (dialog == null) {
            dialog = LoadingDialog(context)
        }
        dialog?.viewModel?.updateHint(message)
        dialog?.show()
    }


    /**
     * hide loading
     */
    fun hideMaterLoading() {
        if (dialog != null) {
            dialog?.dismiss()
            dialog?.onDetachedFromWindow()
        }
        dialog = null
    }
}
