package io.ganguo.core.viewmodel.common.widget

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.ObservableField
import io.ganguo.resources.R
import io.ganguo.core.databinding.WidgetImageViewBinding
import io.ganguo.core.viewmodel.BaseViewModel
import io.image.ImageLoader
import io.image.engine.ImageEngine
import io.image.entity.ImageParam
import io.image.enums.ImageResourceType
import io.image.enums.ImageShapeType
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.resources.ResourcesHelper
import io.support.recyclerview.adapter.diffuitl.IDiffComparator
import java.io.File

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/28
 *   @desc   : 图片ViewModel
 * </pre>
 */
class ImageViewModel(private var shapeType: ImageShapeType) : BaseViewModel<ViewInterface<WidgetImageViewBinding>>(), IDiffComparator<Int> {
    override val layoutId: Int = io.ganguo.core.R.layout.widget_image_view
    private var url = ""
    private var file: File? = null
    private var filePath = ""
    private var drawable: Drawable? = null
    private var bitmap: Bitmap? = null

    @DrawableRes
    private var drawableRes = 0
    private var errorPlaceholder: Drawable? = ImageLoader.get().errorPlaceholder
    private var placeholder: Drawable? = ImageLoader.get().placeholder
    private var imageWidth: Int = ViewGroup.LayoutParams.MATCH_PARENT
    private var imageHeight: Int = ViewGroup.LayoutParams.MATCH_PARENT
    private var radius: Float = 0f
    private var cornerType: ImageEngine.CornerType? = null
    private var resourceType: ImageResourceType = ImageResourceType.NO_SET_DATA
    val scaleType = ObservableField<ImageView.ScaleType>()

    init {
        scaleType.set(ImageView.ScaleType.CENTER_INSIDE)
    }


    override fun onViewAttached(view: View) {
    }


    /**
     * 设置图片加载模式
     * @param scaleType ScaleType
     * @return ImageViewModel
     */
    fun scaleType(scaleType: ImageView.ScaleType) {
        this.scaleType.set(scaleType)
    }

    /**
     * 加载图片链接
     * @param url String
     * @return Builder
     */
    fun url(url: String) {
        this.url = url
        resourceType = ImageResourceType.URL
    }

    /**
     *  若加载来源为 Uri 则将其转成 url
     * @param uri Uri
     * @return Builder
     */
    fun uri(uri: Uri?) {
        url = uri.toString()
        resourceType = ImageResourceType.URL
    }

    /**
     * 加载本地图片文件
     * @param file File
     * @return Builder
     */
    fun file(file: File?) {
        this.file = file
        this.resourceType = ImageResourceType.FILE
    }

    /**
     * 加载本地图片文件
     * @param filePath String
     * @return Builder
     */
    fun filePath(filePath: String) {
        this.filePath = filePath
        this.resourceType = ImageResourceType.FILE_PATH
    }


    /**
     * 加载 drawableRes 资源
     * @param drawableRes Int
     * @return Builder
     */
    fun drawableRes(@DrawableRes drawableRes: Int) {
        this.drawableRes = drawableRes
        this.resourceType = ImageResourceType.DRAWABLE_RES
    }


    /**
     * 加载drawable对象
     * @param drawable Drawable
     * @return Builder
     */
    fun drawable(drawable: Drawable?) {
        this.drawable = drawable
        this.resourceType = ImageResourceType.DRAWABLE
    }

    /**
     * 加载Bitmap对象
     * @param bitmap Bitmap
     * @return Builder
     */
    fun bitmap(bitmap: Bitmap?) {
        this.bitmap = bitmap
        this.resourceType = ImageResourceType.BITMAP
    }


    /**
     *
     * @param width Int
     * @return Builder
     */
    fun imageWidth(width: Int) {
        this.imageWidth = width
    }


    /**
     * 配置加载图片高度
     * @param height Int
     * @return Builder
     */
    fun imageHeight(height: Int) {
        this.imageHeight = height
    }

    /**
     * 图片加载错误占位图
     * @param error Drawable?
     * @return Builder
     */
    fun errorPlaceholder(error: Drawable?) {
        error?.let {
            this.errorPlaceholder = it
        }
    }


    /**
     * 图片加载占位图
     * @param placeholder Drawable?
     * @return Builder
     */
    fun placeholder(placeholder: Drawable?) {
        placeholder?.let {
            this.placeholder = it
        }
    }


    /**
     * @param radius Int
     * @return Builder
     */
    fun radius(cornerType: ImageEngine.CornerType?, radius: Float) {
        this.cornerType = cornerType
        this.radius = radius
    }


    /**
     * 创建图片加载配置参数
     * @return ImageParam
     */
    fun getImageParam(): ImageParam = let {
        val param = ImageParam(context, shapeType)
                .errorPlaceholder(errorPlaceholder)
                .placeholder(placeholder)
                .radius(cornerType, radius)
                .width(imageWidth)
                .height(imageHeight)
        when (resourceType) {
            ImageResourceType.URL -> param.url(url)
            ImageResourceType.BITMAP -> param.bitmap(bitmap)
            ImageResourceType.DRAWABLE -> param.drawable(drawable)
            ImageResourceType.DRAWABLE_RES -> param.drawableRes(drawableRes)
            ImageResourceType.FILE -> param.file(file)
            ImageResourceType.FILE_PATH -> param.filePath(filePath)
            ImageResourceType.NO_SET_DATA -> {
                throw IllegalStateException("Please configure the image data to be loaded!")
            }
            else -> {}
        }
        param
    }

    override fun itemEquals(t: Int): Boolean {
        return t == getItem()
    }

    override fun getItem(): Int {
        return hashCode()
    }

	companion object {

		/**
		 * sample title button
		 *
		 * @param drawableRes
		 * @return ImageViewModel
		 */
		@JvmStatic
		fun sampleTitleButtonVModel(@DrawableRes drawableRes: Int, callback: ((View) -> Unit)): ImageViewModel = let {
			ImageViewModel(ImageShapeType.SQUARE)
					.apply {
						width(ResourcesHelper.getDimensionPixelSize(R.dimen.dp_45))
						height(ViewGroup.LayoutParams.MATCH_PARENT)
						paddingHorizontalRes(R.dimen.dp_15)
						drawableRes(drawableRes)
						click(callback)
					}
		}
	}
}
