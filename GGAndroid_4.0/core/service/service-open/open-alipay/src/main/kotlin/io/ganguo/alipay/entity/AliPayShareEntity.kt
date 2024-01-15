package io.ganguo.alipay.entity

import android.graphics.Bitmap
import io.ganguo.alipay.annotation.AliPayShareType

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/30
 *     desc   : 支付宝分享数据 - 实体类
 * </pre>
 * @property [shareType] 分享类型
 *
 * 以下参数只有当[@see shareType==AliPayShareType.TYPE_WEB_PAGE]才设置
 * @property [webPageUrl] 网页链接
 * @property [title] 分享标题
 * @property [description] 分享描述
 * @property [thumbUrl] 分享缩略图
 *
 * 以下参数主要当[shareType==AliPayShareType.TYPE_TEXT]才设置
 * @property [text] 分享文本内容
 *
 * 以下参数主要当[shareType==AliPayShareType.TYPE_IMAGE_LOCAL_PATH]才设置
 * @property [imageLocalPath] 本地图片路径
 *
 * 以下参数主要当[shareType==AliPayShareType.TYPE_IMAGE_URL]才设置
 * @property [imageUrl] 网络图片路径
 *
 * 以下参数主要当[shareType==AliPayShareType.TYPE_IMAGE_BITMAP]才设置
 * @property [imageBitmap] 位图
 *
 */
data class AliPayShareEntity internal constructor(@setparam:AliPayShareType
                                                  @get:AliPayShareType
                                                  var shareType: String = AliPayShareType.TYPE_TEXT) {
    var webPageUrl: String = ""
    var title: String = ""
    var description: String = ""
    var thumbUrl: String = ""
    var text: String = ""
    var imageLocalPath: String = ""
    var imageUrl: String = ""
    var imageBitmap: Bitmap? = null

    companion object {


        /**
         * 创建Bitmap位图分享对象
         * @param imageBitmap 分享位图
         * @return AliPayShareEntity
         */
        @JvmStatic
        fun newShareImageBitmap(imageBitmap: Bitmap): AliPayShareEntity {
            var entity = AliPayShareEntity(AliPayShareType.TYPE_IMAGE_BITMAP)
            entity.imageBitmap = imageBitmap
            return entity
        }

        /**
         * 创建网络图片分享对象
         * @param imageUrl 网路图片路径
         * @return AliPayShareEntity
         */
        @JvmStatic
        fun newShareImageUrl(imageUrl: String): AliPayShareEntity {
            var entity = AliPayShareEntity(AliPayShareType.TYPE_IMAGE_URL)
            entity.imageUrl = imageUrl
            return entity
        }


        /**
         * 创建本地图片分享对象
         * @param imageLocalPath 本地图片路径
         * @return AliPayShareEntity
         */
        @JvmStatic
        fun newShareImageLocalPath(imageLocalPath: String): AliPayShareEntity {
            var entity = AliPayShareEntity(AliPayShareType.TYPE_IMAGE_LOCAL_PATH)
            entity.imageLocalPath = imageLocalPath
            return entity
        }

        /**
         * 创建文字分享对象
         * @param text 分享文字
         * @return AliPayShareEntity
         */
        @JvmStatic
        fun newShareText(text: String): AliPayShareEntity {
            var entity = AliPayShareEntity(AliPayShareType.TYPE_TEXT)
            entity.text = text
            return entity
        }


        /**
         * 创建网页链接分享对象
         * @param webPageUrl 网页链接
         * @param title 标题
         * @param description 分享描述
         * @param thumbUrl 缩略图链接
         * @return AliPayShareEntity
         */
        @JvmStatic
        fun newShareWebPage(webPageUrl: String, title: String, thumbUrl: String, description: String = ""): AliPayShareEntity {
            var entity = AliPayShareEntity(AliPayShareType.TYPE_WEB_PAGE)
            entity.webPageUrl = webPageUrl
            entity.description = description
            entity.thumbUrl = thumbUrl
            entity.title = title
            return entity
        }


    }
}