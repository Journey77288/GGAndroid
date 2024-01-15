package io.ganguo.demo.view.activity.base

import androidx.databinding.ViewDataBinding
import io.ganguo.demo.R
import io.ganguo.viewmodel.pack.base.activity.BaseVModelActivity

import io.ganguo.viewmodel.pack.common.HeaderItemViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.core.BaseViewModel

/**
 *
 *
 * 基类- 用于封装一些通用的函数
 *
 * Created by leo on 2018/7/30.
 */
abstract class GGVModelActivity<T : ViewDataBinding, B : BaseViewModel<*>> : BaseVModelActivity<T, B>() {


    /**
     * function：create header ViewModel
     *
     * @return
     */
   open fun newHeaderVModel(): HeaderViewModel {
        return HeaderViewModel.Builder()
                .appendItemCenter(HeaderItemViewModel.TitleItemViewModel(javaClass.simpleName))
                .appendItemLeft(HeaderItemViewModel
                        .BackItemViewModel(this)
                        .paddingRes(R.dimen.dp_19)
                        .drawableRes(getBackDrawableRes()))
                .build()
    }

    /**
     * 返回按钮资源
     * @return Int
     */
    protected open fun getBackDrawableRes(): Int {
        return R.drawable.ic_back
    }
}
