package io.ganguo.facebook.entity

import android.graphics.Bitmap
import io.ganguo.facebook.annotation.FBShareContentType

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/01
 *     desc   : Facebook 分享工具类
 * </pre>
 *
 * 以下字段，只有在[type==FBShareContentType.TYPE_WEB_PAGE]时才设置
 * @property [webPageUrl] 网页链接
 * @property [quote] 网页描述
 *
 * 以下字段，只有在[type==FBShareContentType.TYPE_IMAGE_URL||type==FBShareContentType.type==FBShareContentType.TYPE_IMAGE_URL||
 * type==FBShareContentType.type==FBShareContentType.TYPE_IMAGE_URL]时才设置
 *
 * @property [imageBitmap] 位图
 * @property [imageLocalPath] 本地图片路径
 * @property [imageUrl] 网络图片链接
 * @property [isUserGenerated] 是否由用户创建
 * @property [caption] 照片分享时的描述
 *
 * 以下字段，只有在[type==FBShareContentType.TYPE_VIDEO]时，才设置
 * @property [videoLocalPath] 视频本地路径
 * @property [videoThumbBitmap] 视频预览封面
 * @property [description] 描述
 * @property [title] 标题
 *
 * 以下字段，只有在[type==FBShareContentType.TYPE_MEDIA]时，才设置
 * @property videoLocalPaths 视频本地路径集合
 * @property imageLocalPaths 图片本地路径集合
 */
data class FacebookShareEntity internal constructor(@FBShareContentType var type: String) {
    var webPageUrl: String = ""
    var imageBitmap: Bitmap? = null
    var imageLocalPath: String = ""
    var imageUrl: String = ""
    var caption: String = ""
    var videoLocalPath: String = ""
    var videoThumbBitmap: Bitmap? = null
    var title: String = ""
    var description: String = ""
    var quote: String = ""
    var isUserGenerated: Boolean = false
    var imageLocalPaths: MutableList<String> = mutableListOf()
    var videoLocalPaths: MutableList<String> = mutableListOf()

    companion object {


        /**
         * 创建分享网页数据对象
         * @param webPageUrl String 网页链接，必选
         * @param quote String  网页描述
         * @return FacebookShareEntity
         */
        @JvmStatic
        fun newShareWebPage(webPageUrl: String, quote: String = ""): FacebookShareEntity {
            var entity = FacebookShareEntity(FBShareContentType.TYPE_WEB_PAGE)
            entity.webPageUrl = webPageUrl
            entity.quote = quote
            return entity
        }

        /**
         * 创建分享Bitmap图片数据对象
         * @param imageBitmap Bitmap 图片位图，必选
         * @param caption String 图片分享描述，可选
         * @param isUserGenerated Boolean 是否由用户创建图片，可选
         * @return FacebookShareEntity
         */
        @JvmStatic
        fun newShareImageBitmap(imageBitmap: Bitmap,
                                caption: String = "",
                                isUserGenerated: Boolean = false): FacebookShareEntity {
            var entity = FacebookShareEntity(FBShareContentType.TYPE_IMAGE_BITMAP)
            entity.imageBitmap = imageBitmap
            entity.caption = caption
            entity.isUserGenerated = isUserGenerated
            return entity
        }

        /**
         * 创建分享图片链接数据对象
         * @param imageUrl String 网络图片链接，必选
         * @param caption String 图片分享描述，可选
         * @param isUserGenerated Boolean 是否由用户创建图片，可选
         * @return FacebookShareEntity
         */
        @JvmStatic
        fun newShareImageUrl(imageUrl: String,
                             caption: String = "",
                             isUserGenerated: Boolean = false
        ): FacebookShareEntity {
            var entity = FacebookShareEntity(FBShareContentType.TYPE_IMAGE_URL)
            entity.imageUrl = imageUrl
            entity.caption = caption
            entity.isUserGenerated = isUserGenerated
            return entity
        }

        /**
         * 创建分享本地图片数据对象
         * @param imageLocalPath String 本地图片路径，必选
         * @param caption String 图片分享描述，可选
         * @param isUserGenerated Boolean  是否由用户创建图片，可选
         * @return FacebookShareEntity
         */
        @JvmStatic
        fun newShareImageLocalPath(imageLocalPath: String,
                                   caption: String = "",
                                   isUserGenerated: Boolean = false
        ): FacebookShareEntity {
            var entity = FacebookShareEntity(FBShareContentType.TYPE_IMAGE_LOCAL_PATH)
            entity.imageLocalPath = imageLocalPath
            entity.caption = caption
            entity.isUserGenerated = isUserGenerated
            return entity
        }


        /**
         * 创建分享视频数据对象
         * @param videoUrl String 视频本地路径，必选
         * @param videoThumbUrl Bitmap 视频封面位图，可选
         * @param title String 视频标题，可选
         * @param description String 视频描述，可选
         * @return FacebookShareEntity
         */
        @JvmStatic
        fun newShareVideo(videoUrl: String,
                          videoThumbBitmap: Bitmap? = null,
                          title: String = "",
                          description: String = ""): FacebookShareEntity {
            var entity = FacebookShareEntity(FBShareContentType.TYPE_VIDEO)
            entity.videoLocalPath = videoUrl
            entity.videoThumbBitmap = videoThumbBitmap
            entity.title = title
            entity.description = description
            return entity
        }


        /**
         * 创建媒体(视频和照片)分享数据对象
         * @param videoUrl String 视频网络链接
         * @return FacebookShareEntity
         */
        @JvmStatic
        fun newShareMedia(imageLocalPaths: MutableList<String> = mutableListOf(),
                          videoUrls: MutableList<String> = mutableListOf()): FacebookShareEntity {
            var entity = FacebookShareEntity(FBShareContentType.TYPE_MEDIA)
            entity.imageLocalPaths = imageLocalPaths
            entity.videoLocalPaths = videoUrls
            return entity
        }
    }

}