package io.ganguo.viewmodel.pack.base.viewmodel

import android.content.Context

import androidx.databinding.ViewDataBinding
import io.ganguo.viewmodel.core.viewinterface.ViewInterface

import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.view.ViewModelWindow

/**
 * <pre>
 * author : leo
 * time   : 2018/9/15
 * desc   : BaseVModelPopupWindow基类，可能出现通用属性配置的PopupWindow，都直接继承BaseVModelPopupWindow，方便统一添加配置
</pre> *
 */
abstract class BaseVModelPopupWindow<T : ViewDataBinding, B : BaseViewModel<ViewInterface<*>>>(context: Context) : ViewModelWindow<T, B>(context)
