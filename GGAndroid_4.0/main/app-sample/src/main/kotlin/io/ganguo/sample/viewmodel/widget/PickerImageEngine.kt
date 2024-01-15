package io.ganguo.sample.viewmodel.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import coil.imageLoader
import io.ganguo.picker.engine.ImageEngine
import io.image.ImageLoader
import io.image.entity.ImageParam
import io.image.enums.ImageShapeType

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/05/14
 *     desc   : 图片选择器加载引擎
 * </pre>
 */
class PickerImageEngine : ImageEngine {
    override fun loadThumbnail(context: Context?, resize: Int, placeholder: Drawable?, imageView: ImageView?, uri: Uri?) {
        context?.apply {
            val param = ImageParam(context, ImageShapeType.SQUARE)
                    .uri(uri)
                    .placeholder(placeholder)
                    .height(resize)
                    .width(resize)
            ImageLoader.get().displayImage(imageView, param)
        }
    }

    override fun loadImage(context: Context?, resizeX: Int, resizeY: Int, imageView: ImageView?, uri: Uri?) {
        context?.apply {
            val param = ImageParam(context, ImageShapeType.SQUARE)
                    .uri(uri)
            ImageLoader.get().displayImage(imageView, param)
        }
    }

    override fun loadImage(context: Context?, resizeX: Int, resizeY: Int, uri: Uri?, callback: (Drawable) -> Unit) {
        context?.apply {
            val param = ImageParam(context, ImageShapeType.SQUARE)
                    .uri(uri)
                    .width(resizeX)
                    .height(resizeY)
            ImageLoader.get().displayImage(context, param, callback)
        }
    }

    override fun loadVideoThumbnail(context: Context?, resize: Int, placeholder: Drawable?, imageView: ImageView?, uri: Uri?) {
        context?.apply {
            val param = ImageParam(context, ImageShapeType.SQUARE)
                    .uri(uri)
                    .placeholder(placeholder)
                    .height(resize)
                    .width(resize)
            ImageLoader.get().displayVideoFrame(imageView, param)
        }
    }

    override fun loadVideoFirstFrame(context: Context?, resizeX: Int, resizeY: Int, imageView: ImageView?, uri: Uri?) {
        context?.apply {
            val param = ImageParam(context, ImageShapeType.SQUARE)
                    .uri(uri)
            ImageLoader.get().displayVideoFrame(imageView, param)
        }
    }

    override fun loadGifThumbnail(context: Context?, resize: Int, placeholder: Drawable?, imageView: ImageView?, uri: Uri?) {
        context?.apply {
            val param = ImageParam(context, ImageShapeType.SQUARE)
                    .uri(uri)
                    .placeholder(placeholder)
                    .height(resize)
                    .width(resize)
            ImageLoader.get().displayImage(imageView, param)
        }
    }

    override fun loadGifImage(context: Context?, resizeX: Int, resizeY: Int, imageView: ImageView?, uri: Uri?) {
        context?.apply {
            val param = ImageParam(context, ImageShapeType.SQUARE)
                .uri(uri)
                .width(resizeX)
                .height(resizeY)
            ImageLoader.get().displayImage(imageView, param)
        }
    }
}