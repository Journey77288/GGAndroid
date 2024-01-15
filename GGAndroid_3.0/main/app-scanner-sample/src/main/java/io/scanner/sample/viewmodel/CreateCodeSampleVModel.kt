package io.scanner.sample.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.log.core.Logger
import io.ganguo.rx.RxActions
import io.ganguo.scanner.builder.TextViewConfig
import io.ganguo.scanner.helper.EncodedHelper
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.pack.RxVMLifecycle
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.ganguo.viewmodel.pack.common.HFRecyclerViewModel
import io.ganguo.viewmodel.pack.common.HeaderItemViewModel
import io.ganguo.viewmodel.pack.common.HeaderViewModel
import io.ganguo.viewmodel.pack.common.ImageViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.functions.Functions
import io.reactivex.schedulers.Schedulers
import io.scanner.sample.R

/**
 *
 * 生成二维码&&条形码Demo
 *
 * Created by leo on 2018/8/9.
 */
open class CreateCodeSampleVModel : HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>>() {


    /**
     *
     *
     * init header
     *
     */
    override fun initHeader(container: ViewGroup) {
        super.initHeader(container)
        val headerViewModel = HeaderViewModel.Builder()
                .appendItemCenter(HeaderItemViewModel.TitleItemViewModel("生成二维码&&条形码"))
                .appendItemLeft(HeaderItemViewModel.BackItemViewModel(viewInterface.activity))
                .build()
        ViewModelHelper.bind(container, this, headerViewModel)
    }

    override fun onViewAttached(view: View) {
        createCodeBitmap()
    }

    /**
     * function: 生成二维码&&条形码
     *
     * @param
     */
    private fun createCodeBitmap() {
        Observable
                .concat(onCreateQrCode(), onCreateAddLogoQrCode(), onCreateBarCode())
                .subscribeOn(Schedulers.computation())
                .compose(RxVMLifecycle.bindViewModel(this))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { imageViewModels ->
                    Logger.e("createCodeBitmap:imageViewModels:" + imageViewModels.size)
                    adapter.addAll(imageViewModels)
                    adapter.notifyDataSetChanged()
                }
                .doFinally { toggleEmptyView() }
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(javaClass.simpleName + "--createCodeBitmap--"))
    }

    /**
     * function: 生成二维码
     *
     * @return
     */
    private fun onCreateQrCode(): Observable<ImageViewModel> {
        return Observable
                .just("测试生成二维码")
                .map { s -> EncodedHelper.createQrCode(s, getDimensionPixelOffset(R.dimen.dp_200), getDimensionPixelOffset(R.dimen.dp_200)) }
                .map { bitmap -> getImageViewModel(bitmap) }
    }

    /**
     * function: 生成带Logo的二维码
     *
     * @return
     */
    private fun onCreateAddLogoQrCode(): Observable<ImageViewModel> {
        return Observable
                .just("测试生成带Logo的二维码")
                .map { s ->
                    val size = getDimensionPixelOffset(R.dimen.dp_200)
                    val logo = BitmapFactory.decodeResource(context!!.resources, R.drawable.ic_launcher)
                    EncodedHelper.createQrCodeBeltLogo(s, size, size, logo)
                    EncodedHelper.createQrCodeBeltLogo(s, size, size, logo)
                }
                .map { bitmap -> getImageViewModel(bitmap) }
    }

    /**
     * function: 生成条形码
     *
     * @return
     */
    private fun onCreateBarCode(): Observable<ImageViewModel> {
        return Observable.just("1123444343")
                .map { s ->
                    val config = TextViewConfig.Builder()
                            .setColor(getColor(R.color.green))
                            .setGravity(Gravity.CENTER)
                            .build()
                    val size = getDimensionPixelOffset(R.dimen.dp_200)
                    val bitmap = EncodedHelper.createBarCode(context, s, size, size / 2, config)
                    bitmap
                }
                .map { bitmap -> getImageViewModel(bitmap) }
    }

    /**
     * function: create ImageViewModel
     *
     * @param bitmap
     * @return
     */
    private fun getImageViewModel(bitmap: Bitmap): ImageViewModel {
        return ImageViewModel.Builder()
                .width(ImageViewModel.MATCH_PARENT)
                .height(R.dimen.dp_300)
                .setBitmap(bitmap)
                .scaleType(ImageView.ScaleType.CENTER_INSIDE)
                .build()
    }

}
