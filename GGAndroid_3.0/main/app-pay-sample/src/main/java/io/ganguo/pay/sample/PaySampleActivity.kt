package io.ganguo.pay.sample

import io.ganguo.pay.sample.viewmodel.PaySampleViewModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.ganguo.viewmodel.core.view.ViewModelActivity

/**
 *
 *
 * 支付相关Demo
 *
 * Created by leo on 2018/8/7.
 */
class PaySampleActivity : ViewModelActivity<IncludeHfRecyclerBinding, PaySampleViewModel>() {
    override fun createViewModel(): PaySampleViewModel {
        return PaySampleViewModel()
    }

    override fun onViewAttached(viewModel: BaseViewModel<Any>) {

    }
}
