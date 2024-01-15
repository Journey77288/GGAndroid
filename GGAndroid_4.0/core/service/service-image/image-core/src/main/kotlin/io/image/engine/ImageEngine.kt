package io.image.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import io.image.entity.ImageParam
import io.image.enums.ImageResourceType
import io.image.enums.ImageShapeType

/**
 * <pre>
 * author : leo
 * time   : 2019/03/21
 * desc   : 图片加载引擎接口
 * </pre>
 */
interface ImageEngine {
	/**
	 * 恢复加载图片，一般配合滑动列表来用，滑动不加载，停止滑动才加载图片
	 */
	fun resumeLoadImage(context: Context?) {

	}

	/**
	 * 停止加载图片，一般配合滑动列表来用，滑动不加载，停止滑动才加载图片
	 */
	fun pauseLoadImage(context: Context?) {

	}


	/**
	 * 下载图片
	 *
	 * @param context  上下文
	 * @param param 图片资源
	 */
	fun downloadImage(context: Context?, param: ImageParam): Bitmap?


	/**
	 * 默认加载图片 - 根据参数类型选择对应的加载方式
	 * @param imageView ImageView?
	 * @param param ImageParam
	 */
	fun displayImage(imageView: ImageView?, param: ImageParam) {
		check(param.resourceType != ImageResourceType.NO_SET_DATA) {
			"Please configure the image data to be loaded!"
		}
		when (param.shapeType) {
			ImageShapeType.CIRCLE -> {
				displayCircleImage(imageView, param)
			}
			ImageShapeType.ROUND -> {
				displayRoundImage(imageView, param)
			}
			else -> {
				displaySquareImage(imageView, param)
			}
		}
	}

    /**
     * 加载图片(自定义加载回调)
     *
     * @param context Context
     * @param param ImageParam
     * @param callback Function1<Drawable>
     */
    fun displayImage(context: Context, param: ImageParam, callback: (Drawable) -> Unit) {
        check(param.resourceType != ImageResourceType.NO_SET_DATA) {
            "Please configure the image data to be loaded!"
        }
        displaySquareImage(context, param, callback)
    }

	/**
	 * 加载图片
	 * @param imageView ImageView?
	 * @param param ImageParam
	 */
	fun displaySquareImage(imageView: ImageView?, param: ImageParam)

    /**
     * 加载图片(自定义加载回调)
     *
     * @param context Context
     * @param param ImageParam
     * @param callback Function1<Drawable>
     */
	fun displaySquareImage(context: Context, param: ImageParam, callback: ((Drawable) -> Unit))

	/**
	 * 加载圆形图片
	 * @param imageView ImageView?
	 * @param param ImageParam
	 */
	fun displayCircleImage(imageView: ImageView?, param: ImageParam)

	/**
	 * 加载圆角图片
	 * @param imageView ImageView?
	 * @param param ImageParam
	 */
	fun displayRoundImage(imageView: ImageView?, param: ImageParam)

	/**
	 * 加载视频首帧
	 * @param imageView ImageView
	 * @param param 图片加载参数
	 */
	fun displayVideoFrame(imageView: ImageView?, param: ImageParam)

    /**
     * 加载图片原始尺寸
     *
     * @param context Context
     * @param url String
     * @param callback Function1<Pair<Int, Int>, Unit>
     */
    fun loadOriginalSize(context: Context, url: String, callback: (Pair<Int, Int>) -> Unit)

	/**
	 * 图片圆角类型
	 */
	enum class CornerType {
		ALL, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, TOP, BOTTOM, LEFT, RIGHT
	}
}
