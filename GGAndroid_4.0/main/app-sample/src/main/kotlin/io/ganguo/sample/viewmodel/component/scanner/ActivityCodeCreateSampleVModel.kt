package io.ganguo.sample.viewmodel.component.scanner

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.core.viewmodel.common.widget.ImageViewModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.R
import io.ganguo.scanner.builder.TextViewConfig
import io.ganguo.scanner.helper.EncodedHelper
import io.image.enums.ImageShapeType

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/19
 *     desc   : 创建二维码 / 条形码 Demo
 * </pre>
 */
class ActivityCodeCreateSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
    companion object {
        const val TEST_QR_CODE = "测试二维码"
        const val TEST_BAR_CODE = "123456789"
    }

    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_code_create_sample))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        adapter.addAll(listOf(createQrCodeVModel(), createQrCodeWithLogoVModel(), createBarCodeVModel()))
    }

    /**
     * 创建二维码ViewModel
     *
     * @return ImageViewModel
     */
    private fun createQrCodeVModel(): ImageViewModel = let {
        val size = getDimensionPixelSize(io.ganguo.resources.R.dimen.dp_150)
        val bitmap = EncodedHelper.createQrCode(TEST_QR_CODE, size, size)
        createImageViewModel(bitmap)
    }

    /**
     * 创建带logo二维码ViewModel
     *
     * @return ImageViewModel
     */
    private fun createQrCodeWithLogoVModel(): ImageViewModel = let {
        val size = getDimensionPixelSize(io.ganguo.resources.R.dimen.dp_150)
        val logo = BitmapFactory.decodeResource(getResources(), io.ganguo.resources.R.drawable.ic_launcher)
        val bitmap = EncodedHelper.createQrCodeBeltLogo(TEST_BAR_CODE, size, size, logo)
        createImageViewModel(bitmap)
    }

    /**
     * 创建条形码ViewModel
     *
     * @return ImageViewModel
     */
    private fun createBarCodeVModel(): ImageViewModel = let {
        val size = getDimensionPixelSize(io.ganguo.resources.R.dimen.dp_200)
        val config = TextViewConfig.Builder()
                .setColor(getColor(io.ganguo.resources.R.color.colorPrimary))
                .setGravity(Gravity.CENTER)
                .build()
        val bitmap = EncodedHelper.createBarCode(context, TEST_BAR_CODE, size, size / 2, config)
        createImageViewModel(bitmap)
    }

    /**
     * 创建图片ViewModel
     *
     * @return ImageViewModel
     */
    private fun createImageViewModel(bitmap: Bitmap?): ImageViewModel = let {
        ImageViewModel(ImageShapeType.SQUARE)
                .also {
                    it.heightRes(io.ganguo.resources.R.dimen.dp_300)
                    it.bitmap(bitmap)
                    it.marginTopRes(io.ganguo.resources.R.dimen.dp_50)
                    it.backgroundColor(Color.TRANSPARENT)
                    it.scaleType(ImageView.ScaleType.CENTER_INSIDE)
                }
    }
}