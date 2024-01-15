package io.ganguo.viewmodel.pack.base.viewmodel

import android.view.View
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.utils.helper.lazy.ILazyHandler
import io.ganguo.utils.helper.lazy.ILazyLoad
import io.ganguo.utils.helper.lazy.LazyHelper
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/17
 *     desc   : 支持懒加载的HFSRecyclerVModel
 * </pre>
 */
abstract class BaseLazyHFSRecyclerVModel<T : ViewInterface<IncludeHfSwipeRecyclerBinding>> : BasePageHFSRecyclerVModel<T>(), ILazyHandler, ILazyLoad {
    override val lazyHelper: LazyHelper by lazy {
        LazyHelper(this)
    }

    override fun onViewAttached(view: View) {
    }

}