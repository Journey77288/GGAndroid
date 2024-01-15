package io.ganguo.sina.annotation

import androidx.annotation.StringDef

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/08
 *     desc   : 新浪微博分享类型
 * </pre>
 * @property [TYPE_IMAGE] 分享单张图片
 * @property [TYPE_WEB_PAGE] 分享网页
 * @property [TYPE_VIDEO] 分享本地视频
 * @property [TYPE_TEXT] 分享纯文本
 * @property [TYPE_MULTI_IMAGE] 分享多图
 */
@Retention(AnnotationRetention.SOURCE)
@StringDef(
        SinaShareContentType.TYPE_IMAGE,
        SinaShareContentType.TYPE_TEXT,
        SinaShareContentType.TYPE_MULTI_IMAGE,
        SinaShareContentType.TYPE_VIDEO,
        SinaShareContentType.TYPE_WEB_PAGE)
annotation class SinaShareContentType {

    companion object {
        const val TYPE_TEXT = "type_web_page"
        const val TYPE_WEB_PAGE = "type_web_page"
        const val TYPE_VIDEO = "type_video"
        const val TYPE_IMAGE = "type_image"
        const val TYPE_MULTI_IMAGE = "type_multi_image"

    }
}