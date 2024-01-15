package io.ganguo.demo.view.activity

import android.content.Context
import android.content.Intent

import io.ganguo.demo.databinding.ActivitySideBarBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.SideBarViewModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * <pre>
 * author : leo
 * time   : 2019/02/25
 * desc   : SideBar Sample
</pre> *
 */
class SideBarActivity : GGVModelActivity<ActivitySideBarBinding, SideBarViewModel>() {

    override fun createViewModel(): SideBarViewModel {
        return SideBarViewModel()
    }


    override fun onViewAttached(viewModel: SideBarViewModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }


    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, SideBarActivity::class.java)
        }
    }
}
