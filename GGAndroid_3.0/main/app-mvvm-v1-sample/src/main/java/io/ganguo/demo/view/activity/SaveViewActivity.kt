package io.ganguo.demo.view.activity

import io.ganguo.demo.databinding.ActivitySaveViewBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.SaveViewViewModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2019/10/09
 *     desc   :
 * </pre>
 */


class SaveViewActivity : GGVModelActivity<ActivitySaveViewBinding, SaveViewViewModel>() {
    override fun createViewModel(): SaveViewViewModel {
        return SaveViewViewModel()
    }

    override fun onViewAttached(viewModel: SaveViewViewModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }

}