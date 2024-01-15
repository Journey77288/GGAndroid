package io.ganguo.scissor.core

import android.graphics.Bitmap
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import io.ganguo.scissor.utils.Utils
import io.ganguo.scissor.view.widget.CropView
import io.ganguo.scissor.view.widget.CropView.Extensions.LoaderType

class LoadRequest(cropView: CropView) {
    private val cropView: CropView
    private var bitmapLoader: BitmapLoader? = null
    private var loaderType = LoaderType.CLASS_LOOKUP

    /**
     * Load a [Bitmap] using given [BitmapLoader], you must call [LoadRequest.load] afterwards.
     *
     * @param bitmapLoader [BitmapLoader] to use
     * @return current request for chaining, you should call [.load] afterwards.
     */
    fun using(bitmapLoader: BitmapLoader?): LoadRequest {
        this.bitmapLoader = bitmapLoader
        return this
    }

    /**
     * Load a [Bitmap] using the [BitmapLoader] specified by `loaderType`, you must call [ ][LoadRequest.load] afterwards.
     *
     * @param loaderType a reference to the [BitmapLoader] to use
     * @return current request for chaining, you should call [.load] afterwards.
     */
    fun using(loaderType: LoaderType): LoadRequest {
        this.loaderType = loaderType
        return this
    }

    /**
     * Load a [Bitmap] using a [BitmapLoader] into [CropView]
     *
     * @param model Model used by [BitmapLoader] to load desired [Bitmap]
     */
    fun load(model: Any?) {
        if (cropView.width == 0 && cropView.height == 0) {
            // Defer load until layout pass
            deferLoad(model)
            return
        }
        performLoad(model)
    }

    fun performLoad(model: Any?) {
        if (bitmapLoader == null) {
            bitmapLoader = CropViewExtensions.resolveBitmapLoader(cropView, loaderType)
        }
        bitmapLoader!!.load(model!!, cropView)
    }

    fun deferLoad(model: Any?) {
        if (!cropView.viewTreeObserver.isAlive) {
            return
        }
        cropView.viewTreeObserver.addOnGlobalLayoutListener(
                object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        if (cropView.viewTreeObserver.isAlive) {
                            cropView.viewTreeObserver.removeGlobalOnLayoutListener(this)
                        }
                        performLoad(model)
                    }
                }
        )
    }

    init {
        Utils.checkNotNull(cropView, "cropView == null")
        this.cropView = cropView
    }
}