package io.ganguo.viewmodel.pack.helper

import android.content.Context

import androidx.annotation.StringRes
import io.ganguo.utils.helper.ResHelper
import io.ganguo.viewmodel.pack.ui.dialog.LoadingDialog

/**
 * Loading Helper
 * Created by leo on 2018/11/28.
 */
object LoadingDialogHelper {
    private var loadingDialog: LoadingDialog? = null

    /**
     * show loading
     */
    fun showMaterLoading(context: Context, @StringRes msgId: Int) {
        showMaterLoading(context, ResHelper.getString(msgId))
    }


    /**
     * show loading
     */
    fun showMaterLoading(context: Context, message: String) {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.viewModel.setContent(message)
        }
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(context)
        }
        loadingDialog!!.viewModel.setContent(message)
        loadingDialog!!.show()
    }


    /**
     * hide loading
     */
    fun hideMaterLoading() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
        loadingDialog = null
    }

}
