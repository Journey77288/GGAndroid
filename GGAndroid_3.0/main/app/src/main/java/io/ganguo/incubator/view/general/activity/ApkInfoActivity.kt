package io.ganguo.incubator.view.general.activity

import io.ganguo.incubator.viewmodel.general.activity.ApkInfoViewModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.pack.base.activity.BaseVModelActivity
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding

/**
 * <pre>
 * author : leo
 * time   : 2018/11/1
 * desc   : Apk及手机相关参数展示
 * </pre>
 */
class ApkInfoActivity : BaseVModelActivity<IncludeHfRecyclerBinding, ApkInfoViewModel>() {
    override fun createViewModel(): ApkInfoViewModel {
        return ApkInfoViewModel()
    }

    override fun onViewAttached(viewModel: BaseViewModel<Any>) {
    }

}
