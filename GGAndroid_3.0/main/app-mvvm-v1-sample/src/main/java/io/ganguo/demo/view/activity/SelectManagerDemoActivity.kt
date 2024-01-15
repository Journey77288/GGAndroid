package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent

import io.ganguo.demo.view.activity.base.GGHFRVModelActivity
import io.ganguo.demo.viewmodel.activity.SelectManagerDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding

/**
 * SelectManager Demo
 * Created by Roger on 12/05/2017.
 */

class SelectManagerDemoActivity : GGHFRVModelActivity<IncludeHfRecyclerBinding, SelectManagerDemoVModel>() {

    override fun createViewModel(): SelectManagerDemoVModel {
        return SelectManagerDemoVModel()
    }

    override fun onViewAttached(viewModel: SelectManagerDemoVModel) {
    }

    companion object {

        fun intentFor(context: Context): Intent {
            return Intent(context, SelectManagerDemoActivity::class.java)
        }
    }

}
