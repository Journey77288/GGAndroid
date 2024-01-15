package io.scanner.sample

import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.view.ViewModelActivity
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.scanner.sample.viewmodel.CreateCodeSampleVModel

/**
 * <pre>
 * author : leo
 * time   : 2018/10/20
 * desc   : 生成二维码&&条形码Demo
</pre> *
 */
class CreateCodeSampleActivity : ViewModelActivity<IncludeHfRecyclerBinding, CreateCodeSampleVModel>() {
    override fun createViewModel(): CreateCodeSampleVModel {
        return CreateCodeSampleVModel()
    }

    override fun onViewAttached(viewModel: BaseViewModel<Any>) {

    }
}
