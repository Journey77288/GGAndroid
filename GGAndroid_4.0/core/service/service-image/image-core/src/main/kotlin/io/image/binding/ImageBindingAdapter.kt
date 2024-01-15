@file:JvmName("ImageBindingAdapter")

/**
 * <pre>
 *     author : leo
 *     time   : 2020/06/01
 *     desc   : Image DataBinding Adapter
 * </pre>
 */

package io.image.binding

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import io.image.ImageLoader
import io.image.engine.ImageEngine
import io.image.entity.ImageParam
import io.image.enums.ImageShapeType
import java.io.File


/**
 * 绑定自定义配置图片参数
 * @receiver ImageView
 * @param param ImageParam?
 */
@BindingAdapter("android:bind_param_to_image_view")
fun ImageView.bindImageParam(param: ImageParam?) {
    param?.let {
        ImageLoader.get().displayImage(this, it)
    }
}


/**
 * Bind the Uri to the square ImageView
 * @receiver ImageView
 * @param uri Uri?
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_uri_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindSquareUri(uri: Uri?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.SQUARE)
            .uri(uri)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}


/**
 * Bind the Uri to the round ImageView
 * @receiver ImageView
 * @param uri Uri?
 * @param radius Float
 * @param cornerType CornerType
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_round_uri_to_image_view",
			"android:bind_radius_to_image_view",
			"android:bind_corner_type_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindRoundUri(uri: Uri?, radius: Float, cornerType: ImageEngine.CornerType? = ImageEngine.CornerType.ALL, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.ROUND)
            .uri(uri)
            .radius(cornerType, radius)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}

/**
 * Bind the Uri to the Circle ImageView
 * @receiver ImageView
 * @param uri Uri?
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_circle_uri_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindCircleUri(uri: Uri?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.CIRCLE)
            .uri(uri)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}


/**
 * Bind the Bitmap to the square ImageView
 * @receiver ImageView
 * @param bitmap Bitmap?
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_bitmap_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindSquareBitmap(bitmap: Bitmap?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.SQUARE)
            .bitmap(bitmap)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}


/**
 * Bind the Bitmap to the round ImageView
 * @receiver ImageView
 * @param bitmap Bitmap?
 * @param radius Float
 * @param cornerType CornerType
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_round_bitmap_to_image_view",
			"android:bind_radius_to_image_view",
			"android:bind_corner_type_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindRoundDrawable(bitmap: Bitmap?, radius: Float, cornerType: ImageEngine.CornerType= ImageEngine.CornerType.ALL, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.ROUND)
            .bitmap(bitmap)
            .radius(cornerType, radius)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}

/**
 * Bind the Bitmap to the Circle ImageView
 * @receiver ImageView
 * @param bitmap Bitmap?
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_circle_bitmap_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindCircleDrawable(bitmap: Bitmap?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.CIRCLE)
            .bitmap(bitmap)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}


/**
 * Bind the Drawable to the square ImageView
 * @receiver ImageView
 * @param drawable Drawable?
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_drawable_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindSquareDrawable(drawable: Drawable?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.SQUARE)
            .drawable(drawable)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}


/**
 * Bind the Drawable to the round ImageView
 * @receiver ImageView
 * @param drawable Drawable?
 * @param radius Float
 * @param cornerType CornerType
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_round_drawable_to_image_view",
			"android:bind_radius_to_image_view",
			"android:bind_corner_type_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindRoundDrawable(drawable: Drawable?, radius: Float, cornerType: ImageEngine.CornerType= ImageEngine.CornerType.ALL, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.ROUND)
            .drawable(drawable)
            .radius(cornerType, radius)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}

/**
 * Bind the Drawable to the Circle ImageView
 * @receiver ImageView
 * @param drawable Drawable?
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_circle_drawable_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindCircleDrawable(drawable: Drawable?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.CIRCLE)
            .drawable(drawable)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}


/**
 * Bind the drawableRes to the square ImageView
 * @receiver ImageView
 * @param drawableRes Int
 */
@BindingAdapter(
		value = [
			"android:bind_drawable_res_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindSquareDrawableRes(@DrawableRes drawableRes: Int) {
    setImageResource(drawableRes)
}


/**
 * Bind the drawableRes to the round ImageView
 * @receiver ImageView
 * @param drawableRes Int
 * @param radius Float
 * @param cornerType CornerType
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_round_drawable_res_to_image_view",
			"android:bind_radius_to_image_view",
			"android:bind_corner_type_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindRoundDrawableRes(@DrawableRes drawableRes: Int?, radius: Float?, cornerType: ImageEngine.CornerType= ImageEngine.CornerType.ALL, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.ROUND)
            .drawableRes(drawableRes)
            .radius(cornerType, radius)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}


/**
 * Bind the drawableRes to the Circle ImageView
 * @receiver ImageView
 * @param drawableRes Int
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_circle_drawable_res_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindCircleDrawableRes(@DrawableRes drawableRes: Int?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.CIRCLE)
            .drawableRes(drawableRes)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}


/**
 * Bind the url to the square ImageView
 * @receiver ImageView
 * @param url String?
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_url_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindSquareUrl(url: String?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.SQUARE)
            .url(url.orEmpty())
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}


/**
 * Bind the url to the round ImageView
 * @receiver ImageView
 * @param url String?
 * @param radius Float
 * @param cornerType CornerType
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_round_url_to_image_view",
			"android:bind_radius_to_image_view",
			"android:bind_corner_type_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindRoundUrl(url: String?, radius: Float?, cornerType: ImageEngine.CornerType? = ImageEngine.CornerType.ALL, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.ROUND)
            .url(url.orEmpty())
            .radius(cornerType, radius)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}


/**
 * Bind the url to the Circle ImageView
 * @receiver ImageView
 * @param url String?
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_circle_url_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindCircleUrl(url: String?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.CIRCLE)
            .url(url.orEmpty())
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}


/**
 * Bind the filePath to the square ImageView
 * @receiver ImageView
 * @param filePath String?
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_file_path_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindSquareFilePath(filePath: String?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.SQUARE)
            .filePath(filePath.orEmpty())
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}


/**
 * Bind the filePath to the round ImageView
 * @receiver ImageView
 * @param filePath String?
 * @param radius Float
 * @param cornerType CornerType
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_round_file_path_to_image_view",
			"android:bind_radius_to_image_view",
			"android:bind_corner_type_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindRoundFilePath(filePath: String?, radius: Float, cornerType: ImageEngine.CornerType? = ImageEngine.CornerType.ALL, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.ROUND)
            .filePath(filePath.orEmpty())
            .radius(cornerType, radius)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}

/**
 * Bind the filePath to the Circle ImageView
 * @receiver ImageView
 * @param filePath String?
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_circle_file_path_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindCircleFilePath(filePath: String?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    val param = ImageParam(this.context, ImageShapeType.CIRCLE)
            .filePath(filePath.orEmpty())
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
    bindImageParam(param)
}


/**
 * Bind the File to the square ImageView
 * @receiver ImageView
 * @param file File?
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_file_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindSquareFile(file: File?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    file?.let {
        val param = ImageParam(this.context, ImageShapeType.SQUARE)
            .file(it)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
        bindImageParam(param)
    }
}


/**
 * Bind the File to the round ImageView
 * @receiver ImageView
 * @param file File?
 * @param radius Float
 * @param cornerType CornerType
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_round_file_path_to_image_view",
			"android:bind_radius_to_image_view",
			"android:bind_corner_type_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindRoundFile(file: File?, radius: Float, cornerType: ImageEngine.CornerType, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    file?.let {
        val param = ImageParam(this.context, ImageShapeType.ROUND)
            .file(it)
            .radius(cornerType, radius)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
        bindImageParam(param)
    }
}

/**
 * Bind the File to the Circle ImageView
 * @receiver ImageView
 * @param file File?
 * @param placeHolder Drawable?
 * @param errorPlaceHolder Drawable?
 */
@BindingAdapter(
		value = [
			"android:bind_circle_file_to_image_view",
			"android:bind_place_holder_to_image_view",
			"android:bind_error_place_holder_to_image_view"
		],
		requireAll = false
)
fun ImageView.bindCircleFile(file: File?, placeHolder: Drawable?, errorPlaceHolder: Drawable?) {
    file?.let {
        val param = ImageParam(this.context, ImageShapeType.CIRCLE)
            .file(it)
            .placeholder(placeHolder)
            .errorPlaceholder(errorPlaceHolder)
        bindImageParam(param)
    }
}





