package io.ganguo.demo.viewmodel.activity

import android.view.View

import io.ganguo.demo.view.dialog.DemoBottomDialog
import io.ganguo.demo.view.window.REHorizontalWindow
import io.ganguo.demo.view.window.REVerticalWindow
import io.ganguo.demo.view.window.TestPopupWindow
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.viewmodel.pack.common.HFRecyclerViewModel
import io.ganguo.viewmodel.pack.common.item.ItemSampleVModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.reactivex.functions.Consumer

/**
 * Dialog 弹窗 Demo
 * Created by leo on 2018/8/2.
 */

class DialogDemoVModel : HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>>() {
    private var popupWindow: TestPopupWindow? = null


    override fun onViewAttached(view: View) {
        adapter.add(ItemSampleVModel.newItemViewModel("ViewModelPopupWindow", onCreateWindowAction()))
        adapter.add(ItemSampleVModel.newItemViewModel("RecyclerView horizontal PopupWindow", onCreateHTWindowAction()))
        adapter.add(ItemSampleVModel.newItemViewModel("RecyclerView vertical PopupWindow", onCreateVTWindowAction()))
        adapter.add(ItemSampleVModel.newItemViewModel("BottomDialog", onCreateBtmDialogAction()))
        adapter.add(ItemSampleVModel.newItemViewModel("BottomRecyclerDialog", onCreateBtmRVDialogAction()))
        adapter.notifyDataSetChanged()
        toggleEmptyView()
    }


    /**
     * function:create horizontal PopupWindow Consumer
     *
     * @return
     */
    private fun onCreateHTWindowAction(): Consumer<View> {
        return Consumer { view -> REHorizontalWindow(context).showAsDropDown(view) }
    }

    /**
     * function: create vertical PopupWindow Consumer
     *
     * @return
     */
    private fun onCreateVTWindowAction(): Consumer<View> {
        return Consumer { view -> REVerticalWindow(context).showAsDropDown(view) }
    }


    /**
     * function: create PopupWindow Consumer
     *
     * @return
     */
    private fun onCreateBtmRVDialogAction(): Consumer<View> {
        return Consumer { }
    }

    /**
     * function:create PopupWindow Consumer
     *
     * @return
     */
    private fun onCreateBtmDialogAction(): Consumer<View> {
        return Consumer { DemoBottomDialog(context).show() }
    }

    /**
     * function:create PopupWindow Consumer
     *
     * @return
     */
    private fun onCreateWindowAction(): Consumer<View> {
        return Consumer { view ->
            if (getPopWindow().isShowing) {
                getPopWindow().dismiss()
                popupWindow = null
            } else {
                getPopWindow().showAsDropDown(view)
            }
        }
    }


    /**
     *  TestPopupWindow
     *
     * @return
     */
    private fun getPopWindow(): TestPopupWindow {
        if (popupWindow == null) {
            popupWindow = TestPopupWindow(context)
        }
        return popupWindow as TestPopupWindow
    }
}
