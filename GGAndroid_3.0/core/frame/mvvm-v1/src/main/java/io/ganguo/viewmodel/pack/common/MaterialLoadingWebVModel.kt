package io.ganguo.viewmodel.pack.common

import android.view.View
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.databinding.IncludeWebviewBinding
import io.ganguo.viewmodel.pack.common.base.BaseWebViewModel

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/17
 *     desc   : Material 风格Loading
 * </pre>
 */
class MaterialLoadingWebVModel(contentType: BaseWebViewModel.WebContentType, content: String) : BaseWebViewModel<ViewInterface<IncludeWebviewBinding>>(contentType, content) {
    override fun onViewAttached(view: View) {
    }
}