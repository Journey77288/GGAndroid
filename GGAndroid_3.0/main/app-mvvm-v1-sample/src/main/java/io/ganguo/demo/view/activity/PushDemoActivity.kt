package io.ganguo.demo.view.activity

import io.ganguo.demo.databinding.ActivityPushBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.PushDemoViewModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2019/10/10
 *     desc   :
 * </pre>
 */


class PushDemoActivity : GGVModelActivity<ActivityPushBinding, PushDemoViewModel>() {

    override fun createViewModel(): PushDemoViewModel {
        return PushDemoViewModel()
    }


    override fun onViewAttached(viewModel: PushDemoViewModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }

}