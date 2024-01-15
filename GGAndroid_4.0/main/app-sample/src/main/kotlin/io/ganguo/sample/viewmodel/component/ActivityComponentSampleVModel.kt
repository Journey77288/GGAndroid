package io.ganguo.sample.viewmodel.component

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.ganguo.appcompat.Toasts
import io.ganguo.core.databinding.FrameHeaderContentFooterBinding
import io.ganguo.core.databinding.WidgetRecyclerViewBinding
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.frame.HFRecyclerVModel
import io.ganguo.core.viewmodel.common.widget.RecyclerVModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.permission.requestImageMediaPermission
import io.ganguo.rxjava.printThrowable
import io.ganguo.sample.R
import io.ganguo.sample.view.TabSampleActivity
import io.ganguo.sample.view.component.BannerSampleActivity
import io.ganguo.sample.view.component.ExhibitionStateSampleActivity
import io.ganguo.sample.view.component.PhotoCropSampleActivity
import io.ganguo.sample.view.component.SideBarSampleActivity
import io.ganguo.sample.view.component.SingleImageChooseSampleActivity
import io.ganguo.sample.view.component.WebViewSampleActivity
import io.ganguo.sample.view.component.scanner.CodeScannerSampleActivity
import io.ganguo.sample.view.service.picker.MediaPickerDemoActivity
import io.ganguo.sample.viewmodel.ItemMenuVModel
import io.ganguo.single.ImageChooser
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.kotlin.addTo

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/05
 *   @desc   : Component Sample
 * </pre>
 */
class ActivityComponentSampleVModel : HFRecyclerVModel<ActivityInterface<FrameHeaderContentFooterBinding>>() {


    /**
     * 设置标题栏
     * @param header Function0<ViewGroup>
     */
    override fun initHeader(header: () -> ViewGroup) {
        HeaderTitleVModel
                .sampleTitleVModel(viewIF.activity, "Component Sample")
                .let {
                    ViewModelHelper.bind(header.invoke(), this, it)
                }
    }


    /**
     * create RecyclerVModel and bind to ViewBinding
     * @return RecyclerVModel<*, *>
     */
    override fun createRecycleVModel(): RecyclerVModel<*, *> = let {
        RecyclerVModel
                .gridLayout<WidgetRecyclerViewBinding, ViewInterface<WidgetRecyclerViewBinding>>(context, 3, LinearLayoutManager.VERTICAL, 0)
    }


    override fun onViewAttached(view: View) {
        val newViewModels = arrayListOf<ViewModel<*>>()
        newViewModels.add(ItemMenuVModel.create(getString(R.string.str_code_scanner_sample)) {
            CodeScannerSampleActivity.start(context)
        })
        newViewModels.add(ItemMenuVModel.create(getString(R.string.str_exhibition_state_sample)) {
            ExhibitionStateSampleActivity.start(context)
        })
        newViewModels.add(ItemMenuVModel.create(getString(R.string.str_single_image_choose_sample)) {
            SingleImageChooseSampleActivity.start(context)
        })
        newViewModels.add(ItemMenuVModel.create(getString(R.string.str_media_picker_sample)) {
            MediaPickerDemoActivity.start(context)
        })
        newViewModels.add(ItemMenuVModel.create(getString(R.string.str_sticky_header_sample)) {
            SideBarSampleActivity.start(context, false)
        })
        newViewModels.add(ItemMenuVModel.create(getString(R.string.str_banner_sample)) {
            BannerSampleActivity.start(context)
        })
        newViewModels.add(ItemMenuVModel.create(getString(R.string.str_photo_crop_sample)) {
            pickPhotoForCrop()
        })
        newViewModels.add(ItemMenuVModel.create(getString(R.string.str_webview_sample)) {
            WebViewSampleActivity.start(context)
        })
        newViewModels.add(ItemMenuVModel.create(getString(R.string.str_sidebar_sample)) {
            SideBarSampleActivity.start(context, true)
        })
        newViewModels.add(ItemMenuVModel.create(getString(R.string.str_tab_sample)) {
            TabSampleActivity.start(context)
        })
        adapter.addAll(newViewModels)
    }

    /**
     * pick a photo to crop
     */
    private fun pickPhotoForCrop() {
        viewIF.activity.requestImageMediaPermission {
            if (!it.success) {
                Toasts.show(R.string.str_require_storage_permission)
                return@requestImageMediaPermission
            }
            ImageChooser.pickPhoto(context)
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { Uri.fromFile(it) }
                    .doOnNext {
                        PhotoCropSampleActivity.start(context, it)
                    }
                    .doOnError {
                        Toasts.show(it.message.orEmpty())
                    }
                    .subscribe(Functions.emptyConsumer(), printThrowable("--pickPhotoForCrop--"))
                    .addTo(lifecycleComposite)
        }
    }
}
