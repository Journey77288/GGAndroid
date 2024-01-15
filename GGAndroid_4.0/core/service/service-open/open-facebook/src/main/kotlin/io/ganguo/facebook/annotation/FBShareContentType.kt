package io.ganguo.facebook.annotation

import androidx.annotation.StringDef

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/01
 *     desc   : Facebook分享类型
 * </pre>
 * @property TYPE_IMAGE_URL 分享图片链接
 * @property TYPE_IMAGE_LOCAL_PATH  分享本地图片
 * @property TYPE_IMAGE_BITMAP  直接分享Bitmap位图
 * @property TYPE_MEDIA 多媒体(视频和照片，同时分享)
 * @property TYPE_WEB_PAGE 网页
 * @property TYPE_VIDEO 视频
 */
@Retention(AnnotationRetention.SOURCE)
@StringDef(FBShareContentType.TYPE_IMAGE_URL, FBShareContentType.TYPE_MEDIA, FBShareContentType.TYPE_VIDEO, FBShareContentType.TYPE_WEB_PAGE)
annotation class FBShareContentType {
    companion object {
        const val TYPE_WEB_PAGE = "web_page"
        const val TYPE_IMAGE_URL = "image_url"
        const val TYPE_IMAGE_LOCAL_PATH = "image_local_path"
        const val TYPE_IMAGE_BITMAP = "image_bitmap"
        const val TYPE_VIDEO = "video"
        const val TYPE_MEDIA = "media"
    }
}