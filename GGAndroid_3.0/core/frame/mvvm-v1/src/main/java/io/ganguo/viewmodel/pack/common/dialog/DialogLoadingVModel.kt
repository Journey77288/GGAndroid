package io.ganguo.viewmodel.pack.common.dialog

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import io.ganguo.viewmodel.core.ViewModelHelper.bind
import io.ganguo.viewmodel.core.viewinterface.DialogInterface
import io.ganguo.viewmodel.databinding.DialogGgBinding
import io.ganguo.viewmodel.pack.common.base.BaseDialogVModel
import io.ganguo.viewmodel.pack.common.item.ItemLoadingVModel

/**
 * Dialog Loading ViewModel
 * Created by leo on 2018/11/28.
 */
class DialogLoadingVModel : BaseDialogVModel<DialogGgBinding,DialogInterface<DialogGgBinding>>() {
    var content = ObservableField<String>()
    override fun initHeader(container: ViewGroup) {}
    override fun initContent(container: ViewGroup) {
        bind(container, this, ItemLoadingVModel(content))
    }

    override fun initFooter(container: ViewGroup) {}
    override fun onViewAttached(view: View) {}
    fun setContent(content: String): DialogLoadingVModel {
        this.content.set(content)
        return this
    }

    override fun isCancelable(): Boolean {
        return false
    }
}