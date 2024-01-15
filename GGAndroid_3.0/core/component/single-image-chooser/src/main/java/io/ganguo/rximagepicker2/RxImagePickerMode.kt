package io.ganguo.rximagepicker2

import io.ganguo.rximagepicker2.RxImagePickerMode.PHOTO_PICK
import io.ganguo.rximagepicker2.RxImagePickerMode.PHOTO_TAKE

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/11
 *     desc   : 图片选择器模式
 * </pre>
 * @property [PHOTO_TAKE] 拍照
 * @property [PHOTO_PICK] 从相册选择
 */
enum class RxImagePickerMode(var value: Int) {
    PHOTO_TAKE(100),
    PHOTO_PICK(101);
}