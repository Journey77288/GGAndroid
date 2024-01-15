package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent
import io.ganguo.demo.R
import io.ganguo.demo.view.activity.base.GGVModelActivity

import io.ganguo.demo.viewmodel.activity.CollapsingToolbarDemoVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.databinding.IncludeCoordinatorCollapsingToolbarBinding

/**
 * CollapsingToolbar 折叠联动效果 Demo
 * Created by leo on 2018/9/14.
 */
class CollapsingToolbarDemoActivity : GGVModelActivity<IncludeCoordinatorCollapsingToolbarBinding, CollapsingToolbarDemoVModel>() {

    override fun createViewModel(): CollapsingToolbarDemoVModel {
        return CollapsingToolbarDemoVModel()
    }



    override fun newHeaderVModel(): HeaderViewModel {
        return super.newHeaderVModel().apply {
            builder.background(R.color.transparent)
        }
    }

    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, CollapsingToolbarDemoActivity::class.java)
        }
    }

    override fun onViewAttached(viewModel: CollapsingToolbarDemoVModel) {
    }
}
