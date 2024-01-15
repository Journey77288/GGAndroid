package io.ganguo.viewmodel.pack.base.viewmodel

import android.content.Context

import androidx.databinding.ViewDataBinding
import io.ganguo.viewmodel.core.viewinterface.ViewInterface

import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.view.ViewModelDialog

/**
 * <pre>
 * author : leo
 * time   : 2018/9/15
 * desc   : 基类，可能出现通用属性配置的Dialog，都直接继承BaseVModelDialog，方便统一添加配置
</pre> *
 */
abstract class BaseVModelDialog<T : ViewDataBinding, B : BaseViewModel<ViewInterface<*>>>(context: Context, theme: Int) : ViewModelDialog<T, B>(context,theme)
