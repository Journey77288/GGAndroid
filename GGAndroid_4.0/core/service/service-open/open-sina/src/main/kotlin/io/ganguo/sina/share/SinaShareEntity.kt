package io.ganguo.sina.share

import android.graphics.Bitmap
import io.ganguo.sample.sdk.OpenHelper
import io.ganguo.sina.annotation.SinaShareContentType
import java.util.*
import kotlin.collections.ArrayList


/**
 * <pre>
 * author : Leo
 * time   : 2019/8/11
 * desc   : 微博分享数据
 * </pre>
 *
 * @property[thumbImageBitmap] 缩略图
 * @property [title] 标题
 * @property [description] 描述
 * @property [text] 文本内容
 *
 * 以下属性仅仅在[type==SinaShareContentType.TYPE_IMAGE]时，才设置
 * @property [imageBitmap] 位图
 *
 * 以下属性仅仅在[type==SinaShareContentType.TYPE_VIDEO]时，才设置
 * @property [videoLocalPath] 视频本地路径
 *
 * 以下属性仅仅在[type==SinaShareContentType.TYPE_MULTI_IMAGE]时，才设置
 * @property [imageLocalPaths] 多张本地图片路径
 *
 * 以下属性仅仅在[type==SinaShareContentType.TYPE_WEB_PAGE]时，才设置
 * @property [webPageUrl] 网页链接
 * @property [identify] 唯一标识
 */
data class SinaShareEntity internal constructor(@SinaShareContentType var type: String) {
    var imageBitmap: Bitmap? = null
    var thumbImageBitmap: Bitmap? = null
    var title: String = ""
    var description: String = ""
    var text: String = ""
    var webPageUrl: String = ""
    var videoLocalPath: String = ""
    var identify: String = ""
    var imageLocalPaths: ArrayList<String> = arrayListOf()

    companion object {


        /**
         * 创建分享图片数据对象
         * @param imageBitmap Bitmap 位图，必传
         * @param text String 文字内容，可选
         * @return SinaShareEntity
         */
        @JvmStatic
        fun newShareImage(imageBitmap: Bitmap, text: String = ""): SinaShareEntity {
            var entity = SinaShareEntity(SinaShareContentType.TYPE_IMAGE)
            entity.imageBitmap = imageBitmap
            entity.thumbImageBitmap = OpenHelper.scaledBitmapToThumb(imageBitmap)
            entity.text = text
            return entity
        }


        /**
         *  创建分享纯文本内容数据对象
         * @param text String 文本内容，必选
         * @return SinaShareEntity
         */
        @JvmStatic
        fun newShareText(text: String): SinaShareEntity {
            var entity = SinaShareEntity(SinaShareContentType.TYPE_TEXT)
            entity.text = text
            return entity
        }


        /**
         *  创建分享网页数据对象
         * @param webPageUrl String 网页链接，必选
         * @param thumbOriginalBitmap Bitmap 缩略图原图，必选
         * @param title String 标题栏，可选
         * @param description String 描述，可选
         * @param text String 文本内容，可选
         * @param identity String 唯一标识，可选
         * @return SinaShareEntity
         */
        @JvmStatic
        fun newShareWebPage(webPageUrl: String,
                            thumbOriginalBitmap: Bitmap,
                            title: String,
                            description: String = "",
                            text: String = "",
                            identity: String = UUID.randomUUID().toString()): SinaShareEntity {
            var entity = SinaShareEntity(SinaShareContentType.TYPE_WEB_PAGE)
            entity.title = title
            entity.thumbImageBitmap = OpenHelper.scaledBitmapToThumb(thumbOriginalBitmap)
            entity.description = description
            entity.text = text
            entity.identify = identity
            entity.webPageUrl = webPageUrl
            return entity
        }


        /**
         * 创建分享多图数据对象
         * @param text 文本内容，必选
         * @param imageLocalPaths 多张本地图片链接，必选
         * @return SinaShareEntity
         */
        @JvmStatic
        fun newShareMultiImages(text: String, imageLocalPaths: ArrayList<String>): SinaShareEntity {
            var entity = SinaShareEntity(SinaShareContentType.TYPE_MULTI_IMAGE)
            entity.text = text
            entity.imageLocalPaths = imageLocalPaths
            return entity
        }

        /**
         * 创建分享本地视频数据对象
         * @param text String，文本内容，必选
         * @param videoLocalPath String，视频本地路径，必选
         * @return SinaShareEntity
         */
        @JvmStatic
        fun newShareVideo(text: String, videoLocalPath: String): SinaShareEntity {
            var entity = SinaShareEntity(SinaShareContentType.TYPE_VIDEO)
            entity.text = text
            entity.videoLocalPath = videoLocalPath
            return entity
        }

    }
}
