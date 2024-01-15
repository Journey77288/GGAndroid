package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent

import io.ganguo.demo.view.activity.base.GGHFRVModelActivity
import io.ganguo.demo.viewmodel.activity.DialogDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding

/**
 * ViewModelPopupWindow 弹窗Demo
 * Created by leo on 2018/8/2.
 */

class DialogDemoActivity : GGHFRVModelActivity<IncludeHfRecyclerBinding, DialogDemoVModel>() {

    override fun createViewModel(): DialogDemoVModel {
        return DialogDemoVModel()
    }


    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, DialogDemoActivity::class.java)
        }
    }

    override fun onViewAttached(viewModel: DialogDemoVModel) {
    }
}
