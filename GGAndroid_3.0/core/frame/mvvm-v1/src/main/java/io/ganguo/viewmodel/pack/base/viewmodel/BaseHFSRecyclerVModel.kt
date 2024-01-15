package io.ganguo.viewmodel.pack.base.viewmodel

import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.pack.common.HFSRecyclerViewModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding

/**
 * <pre>
 * author : leo
 * time   : 2018/9/15
 * desc   : 基于HFSRecyclerViewModel封装基类，App内要用到HFSRecyclerViewModel的地方，都直接继承BaseHFSRecyclerVModel，便于处理一些通用属性或者后期扩展
</pre> *
 */
abstract class BaseHFSRecyclerVModel<T : ViewInterface<IncludeHfSwipeRecyclerBinding>> : HFSRecyclerViewModel<T>()
