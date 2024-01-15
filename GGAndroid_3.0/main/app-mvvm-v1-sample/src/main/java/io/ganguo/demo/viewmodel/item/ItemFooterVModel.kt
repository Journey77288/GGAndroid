package io.ganguo.demo.viewmodel.item

import android.view.View


import io.ganguo.demo.R
import io.ganguo.demo.databinding.IncludeTestFooterBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.reactivex.functions.Consumer

/**
 * Created by Roger on 6/23/16.
 */

class ItemFooterVModel(private val actionClear: Consumer<View>?) : BaseViewModel<ViewInterface<IncludeTestFooterBinding>>() {
    override val layoutId: Int by lazy { R.layout.include_test_footer }
    override fun onViewAttached(view: View) {

    }

    fun onClear(): View.OnClickListener {
        return View.OnClickListener { v ->
            try {
                actionClear?.accept(v)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}
