package io.ganguo.wechat.share


import android.graphics.Bitmap
import io.ganguo.sample.sdk.OpenHelper
import io.ganguo.wechat.annotation.WXMiniProgramVersionType
import io.ganguo.wechat.annotation.WXShareContentType
import io.ganguo.wechat.annotation.WXShareScene

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 微信分享数据 - 实体类
 * </pre>
 * @property [title] 标题
 * @property [description] 描述
 * @property [scene] [WXShareScene] 分享到微信的渠道
 * @property [type] [WXShareContentType] 分享内容类型
 *
 *
 * 以下字段数据，仅仅在[type==WXShareContentType.TYPE_MUSIC]时，才设置
 * 注意：1、[musicUrl] 和 [musicLowBandUrl] 不能同时为空
 *      2、以下字段都限制长度不超过 10KB
 * @property [musicUrl] 音频网页的 URL 地址
 * @property [musicLowBandUrl] 供低带宽环境下使用的音频网页 URL 地址
 * @property [musicDataUrl] 音频数据的 URL 地址
 * @property [musicLowBandDataUrl] 供低带宽环境下使用的音频数据 URL 地址
 *
 *
 * 以下字段数据，仅仅在[type==WXShareContentType.TYPE_IMAGE]时，才设置
 * @property [imageBitmap] 图片位图，注意做适当压缩，避免OOM
 *
 *
 * 以下字段数据，仅仅在[type==WXShareContentType.TYPE_TEXT]时，才设置
 * @property [text] 文本内容
 *
 * 以下字段数据，仅仅在[type==WXShareContentType.TYPE_VIDEO]时，才设置
 * @property [videoUrl] 视频链接
 * @property [videoLowBandUrl] 低带宽下的视频链接，非必填
 *
 *
 * 以下字段数据，仅仅在[type==WXShareContentType.TYPE_WEB_PAGE]时，才设置
 * @property [webPageUrl] 网页链接
 *
 *
 * 以下字段数据，仅仅在[type==WXShareContentType.TYPE_MINI_PROGRAM]时，才设置
 * @property [webPageUrl] 兼容低版本的网页链接
 * @property [miniAppVersionType] 小程序版本类型 [WXMiniProgramVersionType]
 * @property [miniAppPagePath] 小程序页面路径
 * @property [miniAppId] 小程序原始id
 */
data class WXShareEntity internal constructor(
        @WXShareContentType
        var type: String,
        @WXShareScene
        var scene: Int = WXShareScene.WX_SCENE_SESSION) {

    var musicUrl: String = ""
    var musicLowBandUrl: String = ""
    var musicDataUrl: String = ""
    var musicLowBandDataUrl: String = ""
    var thumbImageBitmapByteArray: ByteArray? = null
    var imageBitmap: Bitmap? = null
    var title: String = ""
    var description: String = ""
    var text: String = ""
    var videoUrl: String = ""
    var videoLowBandUrl: String = ""
    var webPageUrl: String = ""

    @WXMiniProgramVersionType
    var miniAppVersionType: Int = WXMiniProgramVersionType.TYPE_RELEASE
    var miniAppPagePath: String = ""
    var miniAppId: String = ""

    companion object {
        private const val DESCRIPTION_MAX_NUM = 100// 分享描述最大字数限制

        /**
         * 创建微信图片分享数据对象
         * @param type String 分享的内容类型[WXShareContentType]
         * @param scene Int 分享的类型[WXShareScene]
         * @param imageBitmap 分享的位图，无论是分享图片链接、本地路径、图片资源等，都统一转成[bitmap]后，再进行分享
         * @return WXShareEntity
         */
        @JvmStatic
        fun newShareImage(@WXShareScene scene: Int = WXShareScene.WX_SCENE_SESSION, imageBitmap: Bitmap): WXShareEntity {
            var entity = WXShareEntity(WXShareContentType.TYPE_IMAGE, scene)
            entity.imageBitmap = imageBitmap
            entity.thumbImageBitmapByteArray = OpenHelper.bitmapToThumbByteArray(imageBitmap)
            return entity
        }

        /**
         * 创建微信文字分享数据对象
         * @param scene Int 分享的类型[WXShareScene]
         * @param text 文字内容
         * @return WXShareEntity
         */
        fun newShareText(@WXShareScene scene: Int = WXShareScene.WX_SCENE_SESSION, text: String): WXShareEntity {
            var entity = WXShareEntity(WXShareContentType.TYPE_TEXT, scene)
            entity.text = text
            return entity
        }


        /***
         * 创建微信分享音乐数据对象
         * @param scene  Int 分享的类型[WXShareScene]
         * @param musicUrl String 音乐地址，必填
         * @param thumbOriginalBitmap 缩略图原Bitmap位图，必填
         * @param title String 标题，必填
         * @param description String 描述，非必填（最多100个字符）
         * @param musicLowBandUrl String 低带宽下的音乐地址，非必填
         * @param musicDataUrl String 音频数据URL地址，非必填
         * @param musicLowBandDataUrl String 音频数据URL地址，非必填
         * @return WXShareEntity
         */
        fun newShareMusic(@WXShareScene scene: Int = WXShareScene.WX_SCENE_SESSION,
                          musicUrl: String,
                          thumbOriginalBitmap: Bitmap,
                          title: String,
                          description: String = "",
                          musicLowBandUrl: String = "",
                          musicDataUrl: String = "",
                          musicLowBandDataUrl: String): WXShareEntity {
            var entity = WXShareEntity(WXShareContentType.TYPE_MUSIC, scene)
            entity.musicUrl = musicUrl
            entity.musicLowBandDataUrl = musicLowBandUrl
            entity.musicDataUrl = musicDataUrl
            entity.musicLowBandDataUrl = musicLowBandDataUrl
            entity.title = title
            entity.description = description.take(DESCRIPTION_MAX_NUM)
            entity.thumbImageBitmapByteArray = OpenHelper.bitmapToThumbByteArray(thumbOriginalBitmap)
            return entity
        }


        /**
         * 创建微信分享视频数据对象
         * @param scene  Int 分享的类型[WXShareScene]
         * @param videoUrl String 视频地址，必填
         * @param thumbOriginalBitmap Bitmap 缩略图原Bitmap位图，必填
         * @param videoLowBandUrl String  String 低带宽下的视频地址，非必填
         * @param title String 标题，必填
         * @param description String 描述，非必填（最多100个字符）
         * @return WXShareEntity
         */
        fun newShareVideo(@WXShareScene scene: Int = WXShareScene.WX_SCENE_SESSION,
                          videoUrl: String,
                          thumbOriginalBitmap: Bitmap,
                          videoLowBandUrl: String = "",
                          title: String,
                          description: String = ""): WXShareEntity {
            var entity = WXShareEntity(WXShareContentType.TYPE_VIDEO, scene)
            entity.videoUrl = videoUrl
            entity.thumbImageBitmapByteArray = OpenHelper.bitmapToThumbByteArray(thumbOriginalBitmap)
            entity.videoLowBandUrl = videoLowBandUrl
            entity.title = title
            entity.description = description.take(DESCRIPTION_MAX_NUM)
            return entity
        }


        /**
         * 创建微信分享网页数据对象
         * @param scene  Int 分享的类型[WXShareScene]
         * @param webPageUrl String 网页链接，必填
         * @param thumbOriginalBitmap Bitmap 缩略图原Bitmap位图，必填
         * @param title String 标题，必填
         * @param description String 描述，非必填
         * @return WXShareEntity
         */
        fun newShareWebPage(@WXShareScene scene: Int = WXShareScene.WX_SCENE_SESSION,
                            webPageUrl: String,
                            thumbOriginalBitmap: Bitmap,
                            title: String,
                            description: String = ""
        ): WXShareEntity {
            var entity = WXShareEntity(WXShareContentType.TYPE_WEB_PAGE, scene)
            entity.webPageUrl = webPageUrl
            entity.title = title
            entity.description = description.take(DESCRIPTION_MAX_NUM)
            entity.thumbImageBitmapByteArray = OpenHelper.bitmapToThumbByteArray(thumbOriginalBitmap)
            return entity
        }

        /**
         * 创建微信小程序分享对象
         * @param thumbOriginalBitmap Bitmap 缩略图原Bitmap位图，必填
         * @param title String 标题，必填
         * @param description String 描述，非必填（最多100个字符）
         * @param webPageUrl String 兼容低版本的网页链接，非必填
         * @return
         */
        fun newShareMiniProgram(miniAppId: String,
                                @WXMiniProgramVersionType miniVersionType: Int,
                                miniAppPagePath: String,
                                thumbOriginalBitmap: Bitmap,
                                title: String,
                                webPageUrl: String,
                                description: String = ""
        ): WXShareEntity {
            var entity = WXShareEntity(WXShareContentType.TYPE_MINI_PROGRAM, WXShareScene.WX_SCENE_SESSION)
            entity.webPageUrl = webPageUrl
            entity.miniAppId = miniAppId
            entity.miniAppPagePath = miniAppPagePath
            entity.miniAppVersionType = miniVersionType
            entity.thumbImageBitmapByteArray = OpenHelper.bitmapToThumbByteArray(thumbOriginalBitmap)
            entity.title = title
            entity.description = description.take(DESCRIPTION_MAX_NUM)
            return entity
        }

    }
}




