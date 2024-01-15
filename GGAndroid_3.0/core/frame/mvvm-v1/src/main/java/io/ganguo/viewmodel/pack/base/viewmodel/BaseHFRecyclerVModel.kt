package io.ganguo.viewmodel.pack.base.viewmodel

import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.pack.common.HFRecyclerViewModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding

/**
 * <pre>
 * author : leo
 * time   : 2018/9/15
 * desc   : 基于HFRecyclerViewModel封装基类，App内要用到HFRecyclerViewModel的地方，都直接继承BaseHFRecyclerVModel，便于处理一些通用属性或者后期扩展
</pre> *
 */
abstract class BaseHFRecyclerVModel<T : ViewInterface<IncludeHfRecyclerBinding>> : HFRecyclerViewModel<T>()
