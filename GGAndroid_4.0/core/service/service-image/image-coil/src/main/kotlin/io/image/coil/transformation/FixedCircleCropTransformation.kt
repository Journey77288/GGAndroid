package io.image.coil.transformation

import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.core.graphics.applyCanvas
import coil.size.Size
import coil.transform.CircleCropTransformation
import coil.transform.Transformation
import java.lang.Integer.min

/**
 * <pre>
 *     author : lucas
 *     time   : 2023/03/08
 *     desc   : 圆形裁剪 Transformation
 *              修复在自定义系统最小宽度后，圆形内图片没有平铺的问题
 * </pre>
 */
class FixedCircleCropTransformation : Transformation {
    override val cacheKey: String = javaClass.name

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

        val minSize = min(input.width, input.height)
        val radius = minSize / 2f
        val output = createBitmap(minSize, minSize, input.config)
        output.applyCanvas {
            drawCircle(radius, radius, radius, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            scale(input.width / input.getScaledWidth(this).toFloat(), input.height / input.getScaledHeight(this).toFloat())
            drawBitmap(input, radius - input.width / 2f, radius - input.height / 2f, paint)
        }

        return output
    }

    override fun equals(other: Any?) = other is CircleCropTransformation

    override fun hashCode() = javaClass.hashCode()
}