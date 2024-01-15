package io.ganguo.sample.viewmodel.support

import android.app.Activity
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.core.viewmodel.common.widget.TextViewModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R
import io.ganguo.sample.bean.Keys

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : RxActivityResult Sample
 * </pre>
 */
class ActivityRxActivityResultSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
    companion object {
        const val RESULT_DATA = "Hello Android"
    }

    /**
     * 设置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_rx_activity_result_sample))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            addAll(
                listOf(
                    createResultHintVModel(),
                    createButtonVModel()
                )
            )
        }
    }

    /**
     * create activity result hint ViewModel
     *
     * @return TextViewModel
     */
    private fun createResultHintVModel(): TextViewModel {
        return TextViewModel()
                .also {
                    it.marginLeftRes(io.ganguo.resources.R.dimen.dp_15)
                    it.marginTopRes(io.ganguo.resources.R.dimen.dp_10)
                    it.textSizeRes(io.ganguo.resources.R.dimen.font_14)
                    it.textColorRes(io.ganguo.resources.R.color.black)
                    it.text.set(getString(R.string.str_result_data_value, RESULT_DATA))
                }
    }

    /**
     * create button ViewModel
     *
     * @return TextViewModel
     */
    private fun createButtonVModel(): TextViewModel {
        return TextViewModel()
                .also {
                    it.paddingRes(io.ganguo.resources.R.dimen.dp_15, io.ganguo.resources.R.dimen.dp_10, io.ganguo.resources.R.dimen.dp_15, io.ganguo.resources.R.dimen.dp_10)
                    it.marginTopRes(io.ganguo.resources.R.dimen.dp_50)
                    it.marginLeftRes(io.ganguo.resources.R.dimen.dp_15)
                    it.gravity(Gravity.CENTER)
                    it.text(getString(R.string.str_confirm))
                    it.width(ViewGroup.LayoutParams.WRAP_CONTENT)
                    it.backgroundDrawableRes(R.drawable.ripple_button)
                    it.textSizeRes(io.ganguo.resources.R.dimen.font_14)
                    it.textColorRes(io.ganguo.resources.R.color.black)
                    it.click {
                        val intent = Intent()
                        intent.putExtra(Keys.Intent.Common.DATA, RESULT_DATA)
                        viewIF.activity.setResult(Activity.RESULT_OK, intent)
                        viewIF.activity.finish()
                    }
                }
    }
}