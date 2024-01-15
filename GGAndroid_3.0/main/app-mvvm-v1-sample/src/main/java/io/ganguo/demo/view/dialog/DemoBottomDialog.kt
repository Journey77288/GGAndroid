package io.ganguo.demo.view.dialog


import android.content.Context
import io.ganguo.core.R

import io.ganguo.demo.viewmodel.dialog.DialogDemoBottomVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.databinding.DialogGgBinding
import io.ganguo.viewmodel.pack.ui.dialog.BottomVModelDialog

/**
 *
 *
 * 底部弹窗Demo - Dialog
 *
 * Created by leo on 2018/9/30.
 */
class DemoBottomDialog(context: Context,theme: Int= R.style.Dialog_Immersion) : BottomVModelDialog<DialogGgBinding, DialogDemoBottomVModel>(context, theme) {

    override fun createViewModel(): DialogDemoBottomVModel {
        return DialogDemoBottomVModel()
    }


    override fun onViewAttached(viewModel: DialogDemoBottomVModel) {
    }
}
