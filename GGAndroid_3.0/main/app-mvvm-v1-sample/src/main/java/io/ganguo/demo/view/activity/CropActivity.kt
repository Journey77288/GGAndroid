package io.ganguo.demo.view.activity


import io.ganguo.demo.bean.Constants
import io.ganguo.demo.databinding.ActivityCropBinding
import io.ganguo.demo.view.activity.base.GGVModelActivity
import io.ganguo.demo.viewmodel.activity.CropViewModel
import io.ganguo.viewmodel.core.ViewModelHelper


/**
 * <pre>
 *     @author : zoyen
 *     time   : 2019/09/25
 *     desc   : 裁剪页面
 * </pre>
 */

class CropActivity : GGVModelActivity<ActivityCropBinding, CropViewModel>() {

    override fun createViewModel(): CropViewModel {
        var path = intent.getStringExtra(Constants.DATA)
        return CropViewModel(path.orEmpty())
    }

    override fun onViewAttached(viewModel: CropViewModel) {
        ViewModelHelper.bind(binding.includeHeader, viewModel, newHeaderVModel())
    }
}