package io.image.glide.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/09/26
 *     desc   : 图片尺寸
 * </pre>
 */
@Parcelize
class ImageSize(val width: Int, val height: Int) : Parcelable {
}