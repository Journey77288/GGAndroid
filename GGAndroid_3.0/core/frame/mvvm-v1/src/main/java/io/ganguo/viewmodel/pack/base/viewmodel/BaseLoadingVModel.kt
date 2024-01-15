package io.ganguo.viewmodel.pack.base.viewmodel

import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/06
 *     desc   : 页面Loading基类
 * </pre>
 */
abstract class BaseLoadingVModel<T : ViewInterface<*>> : BaseViewModel<T>() {
}