package io.ganguo.demo.viewmodel.dialog

import android.view.Gravity
import android.view.View
import android.view.ViewGroup

import io.ganguo.demo.R
import io.ganguo.viewmodel.pack.common.TextViewModel
import io.ganguo.viewmodel.pack.common.dialog.DialogRecyclerBottomVModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 *
 *
 * 支持RecyclerView的底部弹窗 Demo
 *
 * Created by leo on 2018/9/30.
 */
open class DialogDemoBtmRecyclerVModel : DialogRecyclerBottomVModel() {

    /**
     * item ViewModel
     *
     * @return
     */
    private fun newTextVModel(): BaseViewModel<*> {
        return TextViewModel.Builder()
                .content("test demo")
                .width(TextViewModel.MATCH_PARENT)
                .paddingBottomRes(R.dimen.dp_10)
                .paddingTopRes(R.dimen.dp_10)
                .gravity(Gravity.CENTER)
                .textColorRes(R.color.black)
                .build()
    }

    /**
     *  Header ViewModel
     *
     * @return
     */
    private val titleVModel: BaseViewModel<*>
        get() = TextViewModel.Builder()
                .content("支持RecyclerView的底部弹弹窗")
                .width(TextViewModel.MATCH_PARENT)
                .paddingBottomRes(R.dimen.dp_10)
                .paddingTopRes(R.dimen.dp_10)
                .gravity(Gravity.CENTER)
                .textColorRes(R.color.black)
                .build()


    /**
     * function: Footer ViewModel
     *
     * @return
     */
    private val footerVModel: BaseViewModel<*>
        get() = TextViewModel.Builder()
                .content("取消")
                .width(TextViewModel.MATCH_PARENT)
                .paddingBottomRes(R.dimen.dp_10)
                .paddingTopRes(R.dimen.dp_10)
                .gravity(Gravity.CENTER)
                .backgroundRes(R.color.blue_light_translucent)
                .onClickListener { viewInterface.dialog.dismiss() }
                .textColorRes(R.color.black)
                .build()

    override fun initHeader(container: ViewGroup) {
        ViewModelHelper.bind(container, this, titleVModel)
    }

    override fun initFooter(container: ViewGroup) {
        ViewModelHelper.bind(container, this, footerVModel)
    }

    override fun onViewAttached(view: View) {
        adapter.add(newTextVModel())
        adapter.add(newTextVModel())
        adapter.add(newTextVModel())
        adapter.add(newTextVModel())
        adapter.add(newTextVModel())
        adapter.add(newTextVModel())
        adapter.notifyDataSetChanged()

    }
}
