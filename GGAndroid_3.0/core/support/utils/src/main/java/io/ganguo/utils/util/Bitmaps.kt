package io.ganguo.utils.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.os.Build
import android.widget.ImageView
import io.ganguo.log.core.Logger
import io.ganguo.utils.bean.Globals
import java.io.*
import java.net.URL
import kotlin.math.ceil

/**
 * bitmap处理工具类
 *
 *
 * Created by HulkYao on 4/11/15.
 */
class Bitmaps private constructor() {
    init {
        throw Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR)
    }

    companion object {

        /**
         * 保存为文件
         * (完成后回收bitmap)
         *
         * @param bitmap
         * @param to
         * @param format
         * @param quality
         */
        @JvmStatic
        fun toFile(bitmap: Bitmap, to: File, format: Bitmap.CompressFormat, quality: Int) {
            Validates.notNull(bitmap)
            try {
                val fileOutputStream = FileOutputStream(to)
                bitmap.compress(format, quality, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Logger.e("handleImageToFile failed:", e.message)
            } catch (e: IOException) {
                e.printStackTrace()
                Logger.e("handleImageToFile failed:", e.message)
            }

        }

        /**
         * 保存为PNG
         * (完成后回收bitmap)
         *
         * @param bitmap
         * @param to
         * @param quality
         */
        @JvmStatic
        fun toPNGFile(bitmap: Bitmap, to: File, quality: Int) {
            toFile(bitmap, to, Bitmap.CompressFormat.PNG, quality)
        }

        /**
         * 保存为JPEG
         * (完成后回收bitmap)
         *
         * @param bitmap
         * @param to
         * @param quality
         */
        @JvmStatic
        fun toJPEGFile(bitmap: Bitmap, to: File, quality: Int) {
            toFile(bitmap, to, Bitmap.CompressFormat.JPEG, quality)
        }

        /**
         * 获取bitmap占用的内存体积(byte)
         *
         * @param bitmap byte
         * @return
         */
        @JvmStatic
        fun size(bitmap: Bitmap): Long {
            Validates.notNull(bitmap)
            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> bitmap.allocationByteCount.toLong()
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1 -> bitmap.byteCount.toLong()
                else -> bitmap.rowBytes.toLong() * bitmap.height
            }
        }

        /**
         * 旋转bitmap
         *
         * @param bitmap
         * @param degrees
         * @return bitmap
         */
        @JvmStatic
        fun rotate(bitmap: Bitmap, degrees: Int): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(degrees.toFloat())
            // bitmap new
            return Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.width, bitmap.height, matrix, true)
        }

        /**
         * 旋转图片至正确位置
         *
         * @param bitmap
         * @param path
         * @return
         * @throws IOException
         */
        @JvmStatic
        fun rotateToNormal(bitmap: Bitmap, path: String): Bitmap {
            val angle = readDegree(path)
            return if (angle == 0) {
                bitmap
            } else rotate(bitmap, angle)

        }

        /**
         * 用于压缩file类型文件大小
         *
         * @param inputBitmap
         * @param output
         * @param outPutSize
         * @return
         */
        @JvmStatic
        fun compress(inputBitmap: Bitmap, output: File, outPutSize: Int): File {
            var quality = 100
            // 最小quality是10
            while (quality > 10) {
                quality -= 5
                toJPEGFile(inputBitmap, output, quality)
                val fileSize = output.length() / 1024
                if (fileSize <= outPutSize) {
                    break
                }
            }
            return output
        }

        /**
         * 等比缩放
         *
         * @param from
         * @return
         */
        @JvmStatic
        fun uniformScale(from: File): Bitmap {
            val newOpts = BitmapFactory.Options()
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true
            BitmapFactory.decodeFile(from.path, newOpts)

            newOpts.inJustDecodeBounds = false
            val w = newOpts.outWidth
            val h = newOpts.outHeight

            var be = computeSize(w, h)
            while (true) {
                try {
                    // 设置缩放比例
                    newOpts.inSampleSize = be
                    return BitmapFactory.decodeFile(from.path, newOpts)
                } catch (outOfMemoryError: OutOfMemoryError) {
                    Logger.e("java.lang.OutOfMemoryError,current be:$be")
                    be++
                }

            }
        }


        /**
         * 分辨率的压缩算法
         *
         *
         * 接近微信图片保存时图片分辨率算法
         * https://github.com/Curzibn/Luban/blob/master/library/src/main/java/top/zibin/luban/Engine.java
         *
         * @param srcWidth
         * @param srcHeight
         * @return
         */
        private fun computeSize(srcWidth: Int, srcHeight: Int): Int {
            var width = srcWidth
            var height = srcHeight
            val mSampleSize: Int
            width = if (width % 2 == 1) width + 1 else width
            height = if (height % 2 == 1) height + 1 else height
            width = if (width > height) height else width
            height = if (width > height) width else height
            val scale = width.toDouble() / height
            if (scale <= 1 && scale > 0.5625) {
                if (height < 1664) {
                    mSampleSize = 1
                } else if (height in 1664..4989) {
                    mSampleSize = 2
                } else if (height in 4990..10239) {
                    mSampleSize = 4
                } else {
                    mSampleSize = if (height / 1280 == 0) 1 else height / 1280
                }
            } else if (scale <= 0.5625 && scale > 0.5) {
                mSampleSize = if (height / 1280 == 0) 1 else height / 1280
            } else {
                mSampleSize = ceil(height / (1280.0 / scale)).toInt()
            }
            return mSampleSize
        }

        /**
         * 拿图片路径缩略图
         *
         * @param path 图片的路径
         * @return Bitmap
         */
        fun getThumbBitmap(path: String): Bitmap? {
            var exif: ExifInterface? = null
            try {
                exif = ExifInterface(path)
            } catch (e: IOException) {
                e.printStackTrace()
                Logger.e("failed to get ExifInterface", e.message)
            }

            if (exif == null || exif.thumbnail == null) {
                return null
            }
            val imageData = exif.thumbnail
            return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
        }

        /**
         * 读取图片属性：旋转的角度
         *
         * @param path 图片绝对路径
         * @return degree旋转的角度
         */
        fun readDegree(path: String): Int {
            var degree = 0
            try {
                val exifInterface = ExifInterface(path)
                val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
                when (orientation) {
                    ExifInterface.ORIENTATION_NORMAL -> degree = 0
                    ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
                }
            } catch (e: IOException) {
                Logger.e("failed to readPictureDegree", e.message)
            }

            return degree
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

        /**
         * @param bitmap     原图
         * @param edgeLength 希望得到的正方形部分的边长
         * @return 缩放截取正中部分后的位图。
         */
        fun centerSquareScaleBitmap(bitmap: Bitmap?, edgeLength: Int): Bitmap? {
            if (null == bitmap || edgeLength <= 0) {
                return null
            }
            var result: Bitmap = bitmap
            val widthOrg = bitmap.width
            val heightOrg = bitmap.height
            if (widthOrg > edgeLength && heightOrg > edgeLength) {
                //压缩到一个最小长度是edgeLength的bitmap
                val longerEdge = edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg)
                val scaledWidth = if (widthOrg > heightOrg) longerEdge else edgeLength
                val scaledHeight = if (widthOrg > heightOrg) edgeLength else longerEdge
                val scaledBitmap: Bitmap

                try {
                    scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)
                } catch (e: Exception) {
                    return null
                }

                //从图中截取正中间的正方形部分。
                val xTopLeft = (scaledWidth - edgeLength) / 2
                val yTopLeft = (scaledHeight - edgeLength) / 2

                try {
                    result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength)
                    scaledBitmap.recycle()
                } catch (e: Exception) {
                    return null
                }

            }
            return result
        }

        /**
         * 处理大图导致oom的问题，返回bitmap
         *
         * @param imagePath
         * @return
         */
        fun dealImageSize(imagePath: String): Bitmap? {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(imagePath, options)
            val height = options.outHeight * 720 / options.outWidth
            // 计算图片缩放比例
            options.inSampleSize = computeSampleSize(options, -1, 720 * height)

            options.outWidth = 720
            options.outHeight = height
            // 这样才能真正的返回一个Bitmap给你
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(imagePath, options)
        }


        /**
         * 计算图片大小，Android提供了一种动态计算BitmapFactory.Options() inSampleSize的方法。
         *
         * @param bitmap 位图
         * @return int
         */
        fun computeSampleSize(options: BitmapFactory.Options,
                              minSideLength: Int, maxNumOfPixels: Int): Int {
            val initialSize = computeInitialSampleSize(options, minSideLength,
                    maxNumOfPixels)

            var roundedSize: Int
            if (initialSize <= 8) {
                roundedSize = 1
                while (roundedSize < initialSize) {
                    roundedSize = roundedSize shl 1
                }
            } else {
                roundedSize = (initialSize + 7) / 8 * 8
            }

            return roundedSize
        }

        /**
         * 计算图片大小
         *
         * @param bitmap 位图
         * @return int
         */
        private fun computeInitialSampleSize(options: BitmapFactory.Options,
                                             minSideLength: Int, maxNumOfPixels: Int): Int {
            val w = options.outWidth.toDouble()
            val h = options.outHeight.toDouble()

            val lowerBound = if (maxNumOfPixels == -1)
                1
            else
                Math.ceil(Math.sqrt(w * h / maxNumOfPixels)).toInt()
            val upperBound = if (minSideLength == -1)
                128
            else
                Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength)).toInt()

            if (upperBound < lowerBound) {
                // return the larger one when there is no overlapping zone.
                return lowerBound
            }

            return if (maxNumOfPixels == -1 && minSideLength == -1) {
                1
            } else if (minSideLength == -1) {
                lowerBound
            } else {
                upperBound
            }
        }

        /**
         * 计算图片大小
         *
         * @param bitmap 位图
         * @return int
         */
        @JvmStatic
        fun getBitmapSize(bitmap: Bitmap): Int {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {     //API 19
                return bitmap.allocationByteCount
            }
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
                bitmap.byteCount
            } else bitmap.rowBytes * bitmap.height
        }

        /**
         * 网络资源图片转bitmap
         *
         * @param imageUrl 网络资源图片链接
         * @return Bitmap
         */
        @JvmStatic
        fun httpImageUrlToBitmap(imageUrl: String): Bitmap? {
            val bitmap: Bitmap
            val inPut: InputStream
            val outPut: BufferedOutputStream
            try {
                inPut = BufferedInputStream(URL(imageUrl).openStream(), 1024)
                val dataStream = ByteArrayOutputStream()
                outPut = BufferedOutputStream(dataStream, 1024)
                val b = ByteArray(1024)
                var read: Int = inPut.read(b)
                while (read != -1) {
                    outPut.write(b, 0, read)
                }
                outPut.flush()
                val data = dataStream.toByteArray()
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                return bitmap
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }

        }


        /**
         * 图片文件路径转Bitmap位图
         *
         * @param path       图片路径
         * @param edgeLength 希望得到的正方形部分的边长
         * @return Bitmap
         */
        @JvmStatic
        fun filePathToBitmap(path: String, edgeLength: Int): Bitmap? {
            return fileToBitmap(File(path), edgeLength)
        }


        /**
         * 图片文件转Bitmap位图
         *
         * @param file       文件
         * @param edgeLength 希望得到的正方形部分的边长
         * @return Bitmap
         */
        @JvmStatic
        fun fileToBitmap(file: File, edgeLength: Int): Bitmap? {
            val thumbBmp: Bitmap?
            if (!file.exists()) {
                return null
            }
            //因为一些图片太大，容易导致OOM，所以先处理下
            val bitmap = dealImageSize(file.absolutePath) ?: return null
            //设置缩略图
            thumbBmp = centerSquareScaleBitmap(bitmap, edgeLength)
            if (!bitmap.isRecycled) {
                // 回收图片所占的内存
                bitmap.recycle()
                // 提醒系统及时回收
                System.gc()
            }
            return thumbBmp
        }


        /**
         * recycle bitmap
         *
         * @param bitmap
         */
        @JvmStatic
        fun recycle(bitmap: Bitmap?) {
            if (bitmap == null) {
                return
            }
            if (!bitmap.isRecycled) {
                bitmap.recycle()
            }
        }

        /**
         * recycle drawable
         * -> recycle(Bitmap bitmap)
         *
         * @param drawable
         */
        @JvmStatic
        fun recycle(drawable: Drawable?) {
            if (drawable == null) {
                return
            }
            if (drawable is BitmapDrawable) {
                recycle(drawable.bitmap)
            }
        }

        /**
         * recycle imageView
         * -> recycle(Bitmap bitmap)
         *
         * @param imageView
         */
        @JvmStatic
        fun recycle(imageView: ImageView?) {
            if (imageView == null) {
                return
            }
            val drawable = imageView.drawable
            imageView.setImageBitmap(null)
            imageView.setImageDrawable(null)
            recycle(drawable)
        }
    }

}
