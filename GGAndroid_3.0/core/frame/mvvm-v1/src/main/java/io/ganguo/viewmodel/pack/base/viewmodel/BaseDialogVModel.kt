package io.ganguo.viewmodel.pack.base.viewmodel

import androidx.databinding.ViewDataBinding

import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.DialogInterface

/**
 * <pre>
 * author : leo
 * time   : 2018/9/15
 * desc   : BaseDialogVModel基类，App 内 Dialog ViewModel 如果需要继承BaseViewModel，现在则都改为继承BaseDialogVModel，用于后期配置通用属性或者扩展
</pre> *
 */
abstract class BaseDialogVModel<T : ViewDataBinding> : BaseViewModel<DialogInterface<T>>()
