package io.ganguo.core.view.common.dialog

import android.content.Context
import android.view.ViewGroup
import io.ganguo.resources.R
import io.ganguo.core.databinding.ComponentLoadingDialogBinding
import io.ganguo.core.viewmodel.common.component.LoadingDialogVModel
import io.ganguo.mvvm.view.ViewModelDialog

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/15
 *   @desc   : Loading Dialog
 * </pre>
 */
class LoadingDialog(context: Context) : ViewModelDialog<ComponentLoadingDialogBinding, LoadingDialogVModel>(context, R.style.Dialog_Loading) {
    override val windowWidth: Int by lazy {
        ViewGroup.LayoutParams.MATCH_PARENT
    }
    override val windowHeight: Int by lazy {
        ViewGroup.LayoutParams.MATCH_PARENT
    }

    init {
        setCancelable(false)
    }

    override fun createViewModel(): LoadingDialogVModel {
        return LoadingDialogVModel()
    }

    override fun onViewAttached(viewModel: LoadingDialogVModel) {
    }


}
