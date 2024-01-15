package io.image.glide

import android.graphics.BitmapFactory
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/09/26
 *     desc   : Bitmap尺寸编码器，只读取Bitmap宽高，不加载Bitmap到内存，避免大Bitmap加载出现的OOM
 * </pre>
 */
class BitmapSizeDecoder : ResourceDecoder<File, BitmapFactory.Options> {
    override fun handles(source: File, options: Options): Boolean {
        return true
    }

    override fun decode(source: File, width: Int, height: Int, options: Options): Resource<BitmapFactory.Options>? {
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(source.absolutePath, bmOptions)
        val orientation = getImageOrientation(source)
        // 部分图片经过旋转后宽高度对调
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90 || orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            val actWidth = bmOptions.outHeight
            val actHeight = bmOptions.outWidth
            bmOptions.outWidth = actWidth
            bmOptions.outHeight = actHeight
        }
        return SimpleResource(bmOptions)
    }

    /**
     * 获取图片方向
     *
     * @param source File
     */
    private fun getImageOrientation(source: File): Int {
        var orientation: Int = ExifInterface.ORIENTATION_NORMAL
        try {
            val inputStream = FileInputStream(source)
            val exifInterface = ExifInterface(inputStream)
            orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return orientation
    }
}