package io.ganguo.scissor.core

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import io.ganguo.scissor.bean.CropViewConfig
import io.ganguo.scissor.utils.Utils
import io.ganguo.scissor.view.widget.CropView
import java.io.File
import java.io.OutputStream
import java.util.concurrent.Future

class CropRequest(cropView: CropView) {
    private val cropView: CropView
    private var format = CompressFormat.JPEG
    private var quality: Int = CropViewConfig.Companion.DEFAULT_IMAGE_QUALITY

    /**
     * Compression format to use, defaults to [Bitmap.CompressFormat.JPEG].
     *
     * @return current request for chaining.
     */
    fun format(format: CompressFormat): CropRequest {
        Utils.checkNotNull(format, "format == null")
        this.format = format
        return this
    }

    /**
     * Compression quality to use (must be 0..100), defaults to {@value CropViewConfig#DEFAULT_IMAGE_QUALITY}.
     *
     * @return current request for chaining.
     */
    fun quality(quality: Int): CropRequest {
        Utils.checkArg(quality in 0..100, "quality must be 0..100")
        this.quality = quality
        return this
    }

    /**
     * Asynchronously flush cropped bitmap into provided file, creating parent directory if required. This is performed in another
     * thread.
     *
     * @param file Must have permissions to write, will be created if doesn't exist or overwrite if it does.
     * @return [Future] used to cancel or wait for this request.
     */
    fun into(file: File): Future<Void?> {
        val croppedBitmap = cropView.crop()
        return Utils.flushToFile(croppedBitmap, format, quality, file)
    }

    /**
     * Asynchronously flush cropped bitmap into provided stream.
     *
     * @param outputStream  Stream to write to
     * @param closeWhenDone wetter or not to close provided stream once flushing is done
     * @return [Future] used to cancel or wait for this request.
     */
    fun into(outputStream: OutputStream, closeWhenDone: Boolean): Future<Void?> {
        val croppedBitmap = cropView.crop()
        return Utils.flushToStream(croppedBitmap, format, quality, outputStream, closeWhenDone)
    }

    init {
        Utils.checkNotNull(cropView, "cropView == null")
        this.cropView = cropView
    }
}