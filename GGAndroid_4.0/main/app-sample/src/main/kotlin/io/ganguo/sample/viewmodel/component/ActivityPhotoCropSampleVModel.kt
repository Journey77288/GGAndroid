package io.ganguo.sample.viewmodel.component

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import androidx.lifecycle.lifecycleScope
import io.ganguo.core.helper.LoadingDialogHelper
import io.ganguo.core.viewmodel.common.component.HeaderTitleVModel
import io.ganguo.core.viewmodel.common.widget.TextViewModel
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.sample.AppEnvironment
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ActivityPhotoCropSampleBinding
import io.ganguo.scissor.view.widget.CropView
import io.ganguo.utils.ImageType
import io.ganguo.utils.createImageFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/07
 *     desc   : Photo Crop Sample
 * </pre>
 */
class ActivityPhotoCropSampleVModel(private var uri: Uri?)
    : ViewModel<ActivityInterface<ActivityPhotoCropSampleBinding>>() {
    override val layoutId: Int = R.layout.activity_photo_crop_sample

    private val ratios = arrayOf(1f, 3/4f, 4/3f, 16/9f)
    private lateinit var tempUri: Uri

    override fun onViewAttached(view: View) {
        initHeader()
        loadPhoto()
    }

    /**
     * init header
     */
    private fun initHeader() {
        HeaderTitleVModel.sampleTitleVModel(viewIF.activity, getString(R.string.str_photo_crop))
                .apply {
                    appendRightItem(createConfirmButtonVModel())
                }
                .let {
                    ViewModelHelper.bind(viewIF.binding.flyHeader, this, it)
                }
    }

    /**
     * create header confirm button View Model
     */
    private fun createConfirmButtonVModel(): TextViewModel {
        return HeaderTitleVModel.textButtonViewModel(getString(R.string.str_crop)) {
            cropPhoto()
        }
    }

    /**
     * crop photo
     */
    private fun cropPhoto() {
        LoadingDialogHelper.showMaterLoading(context, getString(R.string.str_cropping))
        val path = AppEnvironment.getCacheFile()?.absolutePath.orEmpty()
        val file = ImageType.JPG.createImageFile(path)
        tempUri = Uri.fromFile(file)
        lifecycleOwner.lifecycleScope.apply {
            launch {
                getCropView()
                    .extensions()
                    .crop()
                    .quality(100)
                    .format(Bitmap.CompressFormat.JPEG)
                    .into(file!!)
                    .get()
                launch(Dispatchers.Main) {
                    uri = tempUri
                    loadPhoto()
                    LoadingDialogHelper.hideMaterLoading()
                }
            }
        }
    }

    /**
     * load photo
     */
    private fun loadPhoto() {
        getCropView().setImageURI(uri)
    }

    /**
     * click the button of changing crop shape
     *
     * @param view
     */
    fun actionChangeShape(view: View) {
        val currentShape = getCropView().getShape()
        if (currentShape == CropView.Shape.RECTANGLE) {
            getCropView().setShape(CropView.Shape.OVAL)
        } else {
            getCropView().setShape(CropView.Shape.RECTANGLE)
        }
    }

    /**
     * click the button of changing viewport ratio
     *
     * @param view
     */
    fun actionChangeViewportRatio(view: View) {
        val currentRatio = getCropView().viewportRatio
        var nextRatio = 1f
        for (i in ratios.indices) {
            if (ratios[i] == currentRatio) {
                nextRatio = if (i == ratios.size - 1) {
                    ratios[0]
                } else {
                    ratios[i + 1]
                }
                break
            }
        }
        getCropView().viewportRatio = nextRatio
    }

    private fun getCropView(): CropView = let {
        viewIF.binding.cvImage
    }
}