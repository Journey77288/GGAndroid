package io.ganguo.open.sdk

import android.content.Context
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/31
 *     desc   : Open Sdk工具类
 * </pre>
 */
class OpenHelper private constructor() {

    companion object {

        /***
         * 转换事务类型（微信、支付宝用到）
         * @param type String
         * @return String
         */
        @JvmStatic
        fun buildTransaction(type: String): String {
            return type + System.currentTimeMillis()
        }


        /**
         * 压缩图片尺寸
         * @param originalBitmap 原图
         * @return Bitmap
         */
        fun scaledBitmapToThumb(originalBitmap: Bitmap): Bitmap {
            return Bitmap.createScaledBitmap(originalBitmap,
                    OpenConstants.BITMAP_WIDTH,
                    OpenConstants.BITMAP_HEIGHT,
                    true)
        }


        /**
         * 图片压缩转换成缩略小图
         * @param bitmap 原图
         * @return
         */
        @JvmStatic
        fun bitmapToThumbByteArray(bitmap: Bitmap): ByteArray {
            var scaleBitmap = scaledBitmapToThumb(bitmap)
            return bitmapToByteArray(scaleBitmap)
        }

        /**
         * 转换资源编码
         *
         * @param bitmap      位图
         * @param needRecycle 是否回收
         * @param quality     图片质量，上限为100
         * @return
         */
        fun bitmapToByteArray(bitmap: Bitmap, quality: Int = 100, needRecycle: Boolean = true): ByteArray {
            val output = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, output)
            if (needRecycle) {
                bitmap.recycle()
            }
            val result = output.toByteArray()
            try {
                output.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

    }
}