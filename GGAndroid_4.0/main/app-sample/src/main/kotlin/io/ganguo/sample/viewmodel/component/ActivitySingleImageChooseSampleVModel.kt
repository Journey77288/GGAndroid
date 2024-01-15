package io.ganguo.sample.viewmodel.component

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.UriUtils
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import io.ganguo.appcompat.Toasts
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.core.viewmodel.common.widget.ImageViewModel
import io.ganguo.core.viewmodel.common.widget.TextViewModel
import io.ganguo.log.core.Logger
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.permissionRx.requestCameraPermissions
import io.ganguo.permissionRx.requestImageMediaPermission
import io.ganguo.rxjava.printThrowable
import io.ganguo.sample.AppEnvironment
import io.ganguo.sample.R
import io.ganguo.sample.viewmodel.ItemMenuVModel
import io.ganguo.single.ImageChooser
import io.ganguo.utils.formatFileSize
import io.ganguo.utils.size
import io.image.engine.ImageEngine
import io.image.enums.ImageShapeType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.launch
import java.io.File

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/19
 *     desc   : Single Image Choose Sample
 * </pre>
 */
class ActivitySingleImageChooseSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {// 兼容Android 13的选择媒体内容方式，不用主动请求接口
            pickMedia = (viewIF.activity as ComponentActivity).registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
                it?.let {
                    onImageChosen(UriUtils.uri2File(it))
                }
            }
        }
    }

    /**
     * 配置标题栏
     *
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        super.initHeader(header)
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_single_image_choose_sample))
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }

    override fun onViewAttached(view: View) {
        with(adapter) {
            addAll(
                listOf(
                    ItemMenuVModel.create(getString(R.string.str_choose_from_album)) {
                        chooseImageFromAlbum()
                    },
                    ItemMenuVModel.create(getString(R.string.str_choose_from_camera)) {
                        chooseImageFromCamera()
                    },
                    createHintVModel()
                )
            )
        }
    }

    /**
     * 创建提示语ViewModel
     *
     * @return TextViewModel
     */
    private fun createHintVModel(): TextViewModel = let {
        TextViewModel()
                .also {
                    it.text(getString(R.string.str_choose_image_result))
                    it.textColorRes(io.ganguo.resources.R.color.black)
                    it.textSizeRes(io.ganguo.resources.R.dimen.font_14)
                    it.marginLeftRes(io.ganguo.resources.R.dimen.dp_16)
                    it.marginTopRes(io.ganguo.resources.R.dimen.dp_50)
                }
    }

    /**
     * 从相册选择图片
     */
    private fun chooseImageFromAlbum() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } else {
            viewIF.activity.requestImageMediaPermission()
                .flatMap {
                    if (it.success) {
                        ImageChooser.pickPhoto(context)
                            .observeOn(AndroidSchedulers.mainThread())
                    } else {
                        Observable.error(Throwable(getString(R.string.str_get_permission_fail)))
                    }
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext { onImageChosen(it) }
                .doOnError { Toasts.show(it.message.orEmpty()) }
                .subscribe(Functions.emptyConsumer(), printThrowable("--chooseImageFromAlbum--"))
                .addTo(lifecycleComposite)
        }
    }

    /**
     * 选择图片后回调
     *
     * @param file
     */
    private fun onImageChosen(file: File) {
        if (adapter.currentList.last() is ImageViewModel) {
            adapter.remove(adapter.currentList.last())
        }
        // 选择图片后进行压缩显示
        viewIF.activity.lifecycleScope.launch {
            val originSize = file.size().formatFileSize()
            val size = getDimensionPixelOffset(io.ganguo.resources.R.dimen.dp_200)
            val compressFile = Compressor.compress(context, file) { default(size, size) }
            val compressFileSize = compressFile.size().formatFileSize()
            adapter.add(createImageVModel(compressFile))
            Logger.i("pick image: origin---$originSize   compress---$compressFileSize")
        }
    }

    /**
     * 从相机选择图片
     */
    private fun chooseImageFromCamera() {
        val path = AppEnvironment.getCacheFile()?.absolutePath.orEmpty()
        viewIF.activity.requestCameraPermissions()
                .flatMap {
                    if (it.success) {
                        ImageChooser.takePhoto(context, path)
                                .observeOn(AndroidSchedulers.mainThread())
                    } else {
                        Observable.error(Throwable(getString(R.string.str_get_permission_fail)))
                    }
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext { onImageChosen(it) }
                .doOnError { Toasts.show(it.message.orEmpty()) }
                .subscribe(Functions.emptyConsumer(), printThrowable("--chooseImageFromCamera--"))
                .addTo(lifecycleComposite)
    }

    /**
     * 创建图片ViewModel
     *
     * @param file
     * @return ImageViewModel
     */
    private fun createImageVModel(file: File): ImageViewModel {
        return ImageViewModel(ImageShapeType.ROUND)
                .also {
                    it.radius(ImageEngine.CornerType.ALL, getDimension(io.ganguo.resources.R.dimen.dp_10))
                    it.file(file)
                    it.size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    it.scaleType(ImageView.ScaleType.CENTER_INSIDE)
                    it.sizeRes(io.ganguo.resources.R.dimen.dp_200, io.ganguo.resources.R.dimen.dp_200)
                    it.backgroundColorRes(io.ganguo.resources.R.color.transparent)
                    it.marginTopRes(io.ganguo.resources.R.dimen.dp_25)
                    it.marginLeftRes(io.ganguo.resources.R.dimen.dp_90)
                }
    }
}