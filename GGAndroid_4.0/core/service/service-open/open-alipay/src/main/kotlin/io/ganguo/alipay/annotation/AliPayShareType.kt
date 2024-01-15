package io.ganguo.alipay.annotation

import androidx.annotation.StringDef

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/30
 *     desc   : 支付宝分享类型
 * </pre>
 */
@Retention(AnnotationRetention.SOURCE)
@StringDef(AliPayShareType.TYPE_IMAGE, AliPayShareType.TYPE_IMAGE_LOCAL_PATH, AliPayShareType.TYPE_IMAGE_URL, AliPayShareType.TYPE_TEXT, AliPayShareType.TYPE_WEB_PAGE)
annotation class AliPayShareType {
    /**
     * @property TYPE_IMAGE 图片类型
     * @property TYPE_IMAGE_LOCAL_PATH 本地图片路径
     * @property TYPE_IMAGE_URL 图片链接
     * @property TYPE_TEXT 文本类型
     * @property TYPE_WEB_PAGE web链接
     */
    companion object {
        internal const val TYPE_IMAGE = "image"
        const val TYPE_IMAGE_BITMAP = "image_bitmap"
        const val TYPE_IMAGE_URL = "image_url"
        const val TYPE_IMAGE_LOCAL_PATH = "image_local_path"
        const val TYPE_TEXT = "text"
        const val TYPE_WEB_PAGE = "webpage"
    }
}