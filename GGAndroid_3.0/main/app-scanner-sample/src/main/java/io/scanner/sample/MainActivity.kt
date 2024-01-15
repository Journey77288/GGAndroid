package io.scanner.sample

import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding
import io.ganguo.viewmodel.core.view.ViewModelActivity
import io.scanner.sample.viewmodel.MainViewModel


/**
 * <pre>
 * author : leo
 * time   : 2018/8/9
 * desc   : Demo 扫码Demo首页
 * </pre>
 */
class MainActivity : ViewModelActivity<IncludeHfSwipeRecyclerBinding, MainViewModel>() {
    override fun createViewModel(): MainViewModel {
        return MainViewModel()
    }

    override fun onViewAttached(viewModel: BaseViewModel<Any>) {}


}
