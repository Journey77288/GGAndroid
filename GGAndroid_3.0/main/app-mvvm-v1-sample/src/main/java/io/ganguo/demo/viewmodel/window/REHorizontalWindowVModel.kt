package io.ganguo.demo.viewmodel.window

import android.graphics.Color
import android.view.View

import androidx.recyclerview.widget.RecyclerView
import io.ganguo.demo.R
import io.ganguo.viewmodel.core.viewinterface.WindowInterface
import io.ganguo.utils.helper.ResHelper
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.viewmodel.pack.common.TextViewModel
import io.ganguo.viewmodel.pack.common.popupwindow.REWindowVModel
import io.ganguo.viewmodel.databinding.IncludeRecyclerPopupwindowBinding
import io.ganguo.viewmodel.core.BaseViewModel

/**
 *
 *
 * Horizontal 方向 - REWindowVModel
 *
 * Created by leo on 2018/10/21.
 */
open class REHorizontalWindowVModel : REWindowVModel<WindowInterface<IncludeRecyclerPopupwindowBinding>>() {

    /**
     * function: 複製
     *
     * @return
     */
    private val copyMenuVModel: BaseViewModel<*>
        get() = TextViewModel.Builder()
                .textColorRes(R.color.black)
                .textSizeRes(R.dimen.font_15)
                .paddingLeftRes(R.dimen.dp_15)
                .paddingRightRes(R.dimen.dp_15)
                .paddingBottomRes(R.dimen.dp_18)
                .paddingTopRes(R.dimen.dp_18)
                .backgroundRes(R.drawable.ripple_default)
                .content("编辑")
                .onClickListener { ToastHelper.showMessage("编辑") }
                .build()

    /**
     * function: 分享
     *
     * @return
     */
    private val shareMenuVModel: BaseViewModel<*>
        get() = TextViewModel.Builder()
                .textColorRes(R.color.black)
                .textSizeRes(R.dimen.font_15)
                .paddingLeftRes(R.dimen.dp_15)
                .paddingRightRes(R.dimen.dp_15)
                .paddingBottomRes(R.dimen.dp_18)
                .paddingTopRes(R.dimen.dp_18)
                .backgroundRes(R.drawable.ripple_default)
                .content("分享")
                .onClickListener { ToastHelper.showMessage("分享") }
                .build()

    /**
     * function: 添加評論
     *
     * @return
     */
    private val addCommentMenuVModel: BaseViewModel<*>
        get() = TextViewModel.Builder()
                .textColorRes(R.color.black)
                .textSizeRes(R.dimen.font_15)
                .paddingLeftRes(R.dimen.dp_15)
                .paddingRightRes(R.dimen.dp_15)
                .paddingBottomRes(R.dimen.dp_18)
                .paddingTopRes(R.dimen.dp_18)
                .backgroundRes(R.drawable.ripple_default)
                .content("复制")
                .onClickListener { ToastHelper.showMessage("复制") }
                .build()

    init {
        val dp5 = ResHelper.getDimensionPixelOffsets(R.dimen.dp_5)
        cardBackgroundColor = Color.WHITE
        cardCornerRadius = dp5
        cardElevation = dp5
        cardMaxElevation = dp5 * 2
        cardMargin = dp5 * 2
        contentPadding = dp5
    }

    override fun onViewAttached(view: View) {
        adapter!!.add(copyMenuVModel)
        adapter!!.add(addCommentMenuVModel)
        adapter!!.add(shareMenuVModel)
        adapter!!.notifyDataSetChanged()
    }

    override fun getOrientation(): Int {
        return RecyclerView.HORIZONTAL
    }
}
