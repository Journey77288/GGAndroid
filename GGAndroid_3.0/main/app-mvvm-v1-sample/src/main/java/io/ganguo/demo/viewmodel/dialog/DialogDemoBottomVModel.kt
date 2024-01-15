package io.ganguo.demo.viewmodel.dialog

import android.view.Gravity
import android.view.View
import android.view.ViewGroup

import io.ganguo.demo.R
import io.ganguo.viewmodel.pack.common.TextViewModel
import io.ganguo.viewmodel.pack.common.dialog.DialogBottomVModel
import io.ganguo.viewmodel.databinding.DialogGgBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper

/**
 *
 *
 * 底部弹出Demo
 *
 * Created by leo on 2018/9/30.
 */
open class DialogDemoBottomVModel : DialogBottomVModel<DialogGgBinding>() {


    /**
     * function: Header ViewModel
     *
     * @return
     */
    private val contentVModel: BaseViewModel<*>
        get() = TextViewModel.Builder()
                .content("我是内容部分")
                .width(TextViewModel.MATCH_PARENT)
                .paddingBottomRes(R.dimen.dp_20)
                .paddingTopRes(R.dimen.dp_20)
                .gravity(Gravity.CENTER)
                .textColorRes(R.color.black)
                .build()

    /**
     * function: Header ViewModel
     *
     * @return
     */
    private val titleVModel: BaseViewModel<*>
        get() = TextViewModel.Builder()
                .content("普通底部Dialog")
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
                .backgroundRes(R.color.blue_light_translucent)
                .paddingTopRes(R.dimen.dp_10)
                .gravity(Gravity.CENTER)
                .onClickListener { viewInterface.dialog.dismiss() }
                .textColorRes(R.color.black)
                .build()

    override fun initContent(container: ViewGroup) {
        super.initContent(container)
        ViewModelHelper.bind(container, this, titleVModel)
        ViewModelHelper.bind(container, this, contentVModel)
        ViewModelHelper.bind(container, this, footerVModel)
    }

    override fun onViewAttached(view: View) {
    }

}
