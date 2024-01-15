package io.ganguo.demo.view.dialog

import android.content.Context
import android.view.ViewGroup

import io.ganguo.demo.databinding.ItemDemoBinding
import io.ganguo.demo.viewmodel.item.ItemDemoVModel
import io.ganguo.viewmodel.core.view.ViewModelDialog
import io.ganguo.utils.util.Randoms

/**
 * Created by Roger on 7/13/16.
 */
class DemoBaseDialog(context: Context) : ViewModelDialog<ItemDemoBinding, ItemDemoVModel<*>>(context) {

    override val dialogWidth: Int
        get() = ViewGroup.LayoutParams.MATCH_PARENT
    override val dialogHeight: Int
        get() = ViewGroup.LayoutParams.MATCH_PARENT

    override fun createViewModel(): ItemDemoVModel<String> {
        return createItemDemoVModel()
    }



    /**
     * function: create Item ViewModel
     *
     * @return
     */
    fun createItemDemoVModel(): ItemDemoVModel<String> {
        val content = Randoms.getRandomCapitalLetters(12)
        return ItemDemoVModel<String>()
                .setBtnText("click")
                .setDataObj(content)
                .setContent(content)
                .setClickAction {
                    dismiss()
                }
    }

    override fun onViewAttached(viewModel: ItemDemoVModel<*>) {
    }

}
