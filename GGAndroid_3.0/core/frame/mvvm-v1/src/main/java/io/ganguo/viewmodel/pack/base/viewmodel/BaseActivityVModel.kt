package io.ganguo.viewmodel.pack.base.viewmodel

import androidx.databinding.ViewDataBinding

import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface

/**
 * <pre>
 * author : leo
 * time   : 2018/9/15
 * desc   : BaseActivityVModel基类 - App内Activity ViewModel如果需要继承BaseViewModel，现在则都改为继承BaseActivityVModel，方便统一添加配置
</pre> *
 */
abstract class BaseActivityVModel<T : ViewDataBinding> : BaseViewModel<ActivityInterface<T>>()
