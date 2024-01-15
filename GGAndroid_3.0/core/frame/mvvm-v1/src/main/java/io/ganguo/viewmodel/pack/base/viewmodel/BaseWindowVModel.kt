package io.ganguo.viewmodel.pack.base.viewmodel

import androidx.databinding.ViewDataBinding
import io.ganguo.viewmodel.core.viewinterface.WindowInterface
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * <pre>
 * author : leo
 * time   : 2018/10/20
 * desc   : BaseWindowVModel基类 - App内PopupWindow ViewModel如果需要继承BaseViewModel，现在则都改为继承BaseWindowVModel
</pre> *
 */
abstract class BaseWindowVModel<T : ViewDataBinding> : BaseViewModel<WindowInterface<T>>()
