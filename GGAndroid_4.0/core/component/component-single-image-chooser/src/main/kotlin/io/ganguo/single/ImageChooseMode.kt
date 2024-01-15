package io.ganguo.single

import io.ganguo.single.ImageChooseMode.PICK_PHOTO
import io.ganguo.single.ImageChooseMode.TAKE_PHOTO

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/11
 *     desc   : 图片选择器模式
 * </pre>
 * @property [TAKE_PHOTO] 拍照
 * @property [PICK_PHOTO] 从相册选择
 */
internal enum class ImageChooseMode(var value: Int) {
    TAKE_PHOTO(100),
    PICK_PHOTO(101),
    PICK_VIDEO(102);
}