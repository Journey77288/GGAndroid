package io.ganguo.line.annotation

import androidx.annotation.StringDef

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/04
 *     desc   : Line分享类型
 * </pre>
 * @property [TYPE_CONTENT] 分享文本内容
 * @property [TYPE_IMAGE] 分享图片
 * @property [LineShareContentType.TYPE_WEB_PAGE] 分享网页
 */
@Retention(AnnotationRetention.SOURCE)
@StringDef(LineShareContentType.TYPE_WEB_PAGE, LineShareContentType.TYPE_IMAGE, LineShareContentType.TYPE_CONTENT)
annotation class LineShareContentType {
    companion object {
        const val TYPE_CONTENT = "content"
        const val TYPE_WEB_PAGE = "web_page"
        const val TYPE_IMAGE = "image"
    }
}