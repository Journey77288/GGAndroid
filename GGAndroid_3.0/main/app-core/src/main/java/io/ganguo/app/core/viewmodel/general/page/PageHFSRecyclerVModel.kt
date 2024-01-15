package io.ganguo.app.core.viewmodel.general.page

import io.ganguo.http2.core.use.response.paging.IPagingHandler
import io.ganguo.http2.core.use.response.paging.PagingHelper
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.pack.base.viewmodel.BaseHFSRecyclerVModel
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/16
 *     desc   : 支持网络请求分页处理的HFSRecyclerVModel
 * </pre>
 */
abstract class PageHFSRecyclerVModel<T : ViewInterface<IncludeHfSwipeRecyclerBinding>> : BaseHFSRecyclerVModel<T>(), IPagingHandler {
    override val pageHelper: PagingHelper by lazy {
        PagingHelper()
    }
}
