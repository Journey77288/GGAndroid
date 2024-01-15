package io.ganguo.demo.viewmodel.page

import android.view.View

import io.ganguo.demo.R
import io.ganguo.demo.databinding.PageEmptyDataBinding
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * 空数据提示页面
 * Created by leo on 2018/11/10.
 */
class EmptyDataViewModel : BaseViewModel<ViewInterface<PageEmptyDataBinding>>() {
    override fun onViewAttached(view: View) {

    }

    override val layoutId: Int by lazy { R.layout.page_empty_data }
}
