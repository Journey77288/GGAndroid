package io.ganguo.line.share

import android.graphics.Bitmap
import io.ganguo.line.annotation.LineShareContentType

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/04
 *     desc   : Line分享
 * </pre>
 *
 * 以下属性仅在[type==TYPE_IMAGE]时，设置
 * @property [imageBitmap]
 *
 *
 * 以下属性仅在[type==TYPE_WEB_PAGE]时，设置
 * @property[title] 标题
 * @property[webPageUrl] 网页链接
 *
 */
data class LineShareEntity internal constructor(@LineShareContentType var type: String) {
    var imageBitmap: Bitmap? = null
    var title: String = ""
    var content: String = ""
    var webPageUrl = ""

    companion object {


        /**
         * 分享图片
         * @param bitmap 分享图片位图，必选
         * @return LineShareEntity
         */
        @JvmStatic
        fun newShareImage(imageBitmap: Bitmap): LineShareEntity {
            var entity = LineShareEntity(LineShareContentType.TYPE_IMAGE)
            entity.imageBitmap = imageBitmap
            return entity
        }


        /**
         * 分享网页
         * @param title String 标题，必选
         * @param webPageUrl String 网页链接，必选
         * @return LineShareEntity
         */
        @JvmStatic
        fun newShareWebPage(title: String, webPageUrl: String): LineShareEntity {
            var entity = LineShareEntity(LineShareContentType.TYPE_WEB_PAGE)
            entity.title = title
            entity.webPageUrl = webPageUrl
            return entity
        }

        /**
         * 分享文本
         * @param title String 标题，必选
         * @param cotent String 文本内容，必选
         * @return LineShareEntity
         */
        fun newShareText(title: String, content: String): LineShareEntity {
            var entity = LineShareEntity(LineShareContentType.TYPE_CONTENT)
            entity.title = title
            entity.content = content
            return entity
        }
    }
}