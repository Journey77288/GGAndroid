package io.ganguo.viewmodel.pack.ui.dialog

import android.content.Context
import io.ganguo.core.R
import io.ganguo.viewmodel.databinding.DialogGgBinding
import io.ganguo.viewmodel.pack.common.dialog.DialogLoadingVModel

/**
 * Dialog Loading
 * Created by leo on 2018/11/28.
 */
class LoadingDialog(context: Context, theme: Int= R.style.Dialog_Immersion) : FullScreenVModelDialog<DialogGgBinding, DialogLoadingVModel>(context,theme) {
    override fun createViewModel(): DialogLoadingVModel {
        TODO("Not yet implemented")
    }

    override fun onViewAttached(viewModel: DialogLoadingVModel) {
        TODO("Not yet implemented")
    }

}