package io.ganguo.sample.viewmodel.service.picker

import android.os.Build
import android.view.View
import android.view.ViewGroup
import io.ganguo.appcompat.Toasts
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.component.PageLoadingVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.permission.requestAudioMediaPermission
import io.ganguo.permission.requestImageMediaPermission
import io.ganguo.permission.requestStoragePermissions
import io.ganguo.permission.requestVideoMediaPermission
import io.ganguo.picker.core.entity.Media
import io.ganguo.picker.core.entity.PickerSpec
import io.ganguo.picker.ui.PickerActivity
import io.ganguo.rxresult.ActivityResult
import io.ganguo.sample.R
import io.ganguo.sample.context.AppContext
import io.ganguo.sample.viewmodel.ItemMenuVModel
import io.image.ImageLoader
import io.image.coil.CoilImageEngine

/**
 * <pre>
 *   @author : leo
 *   @time : 2020/12/08
 *   @desc : 媒体文件选择库 - 配置demo
 * </pre>
 */
class ActivityMediaPickerDemoVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {

    init {
        addLoadingViewCreator(PageLoadingVModel(this))
        initImageEngine()
    }

    /**
     * 设置标题栏
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
	    HeaderTitleVModel.sampleTitleVModel(viewIF.activity, "媒体文件选择库Demo（Coli）")
			    .let {
				    ViewModelHelper.bind(header.invoke(), this, it)
			    }
    }


    override fun onViewAttached(view: View) {
        showLoadingView()
        with(adapter) {
            addAll(
                createSingleImageVModel(),
                createMultiImagesVModel(),
                createSingleVideoVModel(),
                createMultiVideosVModel(),
                createSingleAudioVModel(),
                createMultiAudiosVModel(),
                createSingleImageOrVideoVModel(),
                createMultiImagesOrVideosVModel()
            )
            toggleEmptyView()
        }
    }


    /**
     * 单选图片
     */
    private fun createSingleImageVModel(): ViewModel<*> = ItemMenuVModel.create("单选图片") {
        viewIF.activity.requestImageMediaPermission {
            if (!it.success) {
                Toasts.show(R.string.str_require_storage_permission)
                return@requestImageMediaPermission
            }
            PickerSpec.pickImage(viewIF.activity, imageNum = 1) { activityResult ->
                if (activityResult.isResultOK) {
                    Toasts.show(getResultMedias(activityResult).toString())
                }
            }
        }
    }

    private fun getResultMedias(activityResult: ActivityResult): ArrayList<Media>? {
        val resultList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activityResult?.data?.getParcelableArrayListExtra(PickerActivity.EXTRA_RESULT_SELECTION, Media::class.java)
        } else {
            activityResult?.data?.getParcelableArrayListExtra<Media>(PickerActivity.EXTRA_RESULT_SELECTION)
        }
        return resultList
    }

    /**
     * 多选图片
     */
    private fun createMultiImagesVModel(): ViewModel<*> = ItemMenuVModel.create("多选图片") {
        viewIF.activity.requestImageMediaPermission {
            if (!it.success) {
                Toasts.show(R.string.str_require_storage_permission)
                return@requestImageMediaPermission
            }
            PickerSpec.pickImage(viewIF.activity, imageNum = 5) {activityResult ->
                if (activityResult.isResultOK) {
                    Toasts.show(getResultMedias(activityResult).toString())
                }
            }
        }
    }

    /**
     * 单选视频
     */
    private fun createSingleVideoVModel(): ViewModel<*> = ItemMenuVModel.create("单选视频") {
        viewIF.activity.requestVideoMediaPermission {
            if (!it.success) {
                Toasts.show(R.string.str_require_storage_permission)
                return@requestVideoMediaPermission
            }
            PickerSpec.pickVideo(viewIF.activity, videoNum = 1) { activityResult ->
                if (activityResult.isResultOK) {
                    Toasts.show(getResultMedias(activityResult).toString())
                }
            }
        }
    }

    /**
     * 多选视频
     */
    private fun createMultiVideosVModel(): ViewModel<*> = ItemMenuVModel.create("多选视频") {
        viewIF.activity.requestVideoMediaPermission {
            if (!it.success) {
                Toasts.show(R.string.str_require_storage_permission)
                return@requestVideoMediaPermission
            }
            PickerSpec.pickVideo(viewIF.activity, videoNum = 5) {activityResult ->
                if (activityResult.isResultOK) {
                    Toasts.show(getResultMedias(activityResult).toString())
                }
            }
        }
    }

    /**
     * 单选音频
     */
    private fun createSingleAudioVModel(): ViewModel<*> = ItemMenuVModel.create("单选音频") {
        viewIF.activity.requestAudioMediaPermission {
            if (!it.success) {
                Toasts.show(R.string.str_require_storage_permission)
                return@requestAudioMediaPermission
            }
            PickerSpec.pickAudio(viewIF.activity, audioNum = 1) {activityResult ->
                if (activityResult.isResultOK) {
                    Toasts.show(getResultMedias(activityResult).toString())
                }
            }
        }
    }

    /**
     * 多选音频
     */
    private fun createMultiAudiosVModel(): ViewModel<*> = ItemMenuVModel.create("多选音频") {
        viewIF.activity.requestAudioMediaPermission {
            if (!it.success) {
                Toasts.show(R.string.str_require_storage_permission)
                return@requestAudioMediaPermission
            }
            PickerSpec.pickAudio(viewIF.activity, audioNum = 5) {activityResult ->
                if (activityResult.isResultOK) {
                    Toasts.show(getResultMedias(activityResult).toString())
                }
            }
        }
    }

    /**
     * 单选图片或视频
     */
    private fun createSingleImageOrVideoVModel(): ViewModel<*> = ItemMenuVModel.create("单选 图片或视频") {
        viewIF.activity.requestStoragePermissions {
            if (!it.success) {
                Toasts.show(R.string.str_require_storage_permission)
                return@requestStoragePermissions
            }
            PickerSpec.pickImageOrVideo(viewIF.activity, limitNum = 1) {activityResult ->
                if (activityResult.isResultOK) {
                    Toasts.show(getResultMedias(activityResult).toString())
                }
            }
        }
    }

    /**
     * 多选图片或视频
     */
    private fun createMultiImagesOrVideosVModel(): ViewModel<*> = ItemMenuVModel.create("多选 图片和视频") {
        viewIF.activity.requestStoragePermissions {
            if (!it.success) {
                Toasts.show(R.string.str_require_storage_permission)
                return@requestStoragePermissions
            }
            PickerSpec.pickImageOrVideo(viewIF.activity, limitNum = 5) {activityResult ->
                if (activityResult.isResultOK) {
                    Toasts.show(getResultMedias(activityResult).toString())
                }
            }
        }
    }


    /**
     * 初始化图片加载引擎
     */
    private fun initImageEngine() {
        val engine = CoilImageEngine
                .Builder(AppContext.me())
                .supportGif(true)
                .supportSvg(true)
                .supportVideoFrame(true)
                .build()

        ImageLoader
                .Builder(engine)
                .apply {
                    ImageLoader.init(this)
                }
    }
}
