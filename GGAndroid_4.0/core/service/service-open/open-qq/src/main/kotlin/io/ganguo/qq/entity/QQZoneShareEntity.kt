package io.ganguo.qq.entity

import io.ganguo.qq.annotation.QQMiniProgramVersionType
import io.ganguo.qq.annotation.QQShareContentType
import io.ganguo.qq.annotation.QQZoneShareContentType

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/02
 *     desc   : QQ空间分享数据类
 * </pre>
 * @property [webPageUrl] 网页链接
 * @property [title]标题栏
 * @property [summary]描述
 * @property [imageUrls] 图片链接集合，最多9张，多出的会被丢弃
 * @property [videoLocalPath] 视频本地路径
 * @property [extraScene] 用于区分分享场景，异化feeds点击行为和小尾巴展示(官方文档是这么说的，不明觉厉)
 * @property [callbackText] 回调信息，一般用不到
 * @property [miniAppId] 小程序appId
 * @property [miniAppPagePath] 小程序路径
 * @property [miniAppVersionType] 小程序版本类型
 * @property [imageUrl] 封面图片链接
 */
class QQZoneShareEntity internal constructor(@QQZoneShareContentType var type: Int) {
    var webPageUrl: String = ""
    var title: String = ""
    var summary: String = ""
    var imageUrl: String = ""
    var imageUrls: ArrayList<String> = arrayListOf()
    var videoLocalPath: String = ""
    var extraScene: String = ""
    var callbackText: String = ""
    @QQMiniProgramVersionType
    var miniAppVersionType: Int = QQMiniProgramVersionType.TYPE_RELEASE
    var miniAppPagePath: String = ""
    var miniAppId: String = ""


    companion object {

        /***
         * 生成网页分享数据类
         * @param title 标题，必填
         * @param webPageUrl 网页链接，必填
         * @param summary 描述，可选
         * @param imageUrls 图片链接地址，可选
         * @return [QQZoneShareEntity]
         */
        @JvmStatic
        fun newWebPage(webPageUrl: String,
                       title: String,
                       summary: String = "",
                       imageUrls: ArrayList<String> = arrayListOf()): QQZoneShareEntity {
            var entity = QQZoneShareEntity(QQZoneShareContentType.TYPE_WEB_PAGE)
            entity.title = title
            entity.imageUrls = imageUrls
            entity.webPageUrl = webPageUrl
            entity.summary = summary
            return entity
        }


        /***
         * 生成发布图文说说数据类
         * @param summary 描述，必选
         * @param imageUrls 图片集合，可选
         * @param extraScene 标识，可选
         * @param callbackText 回调文字，可选
         * @return [QQZoneShareEntity]
         */
        @JvmStatic
        fun newPublishImageText(
                summary: String,
                imageUrls: ArrayList<String> = arrayListOf(),
                extraScene: String = "",
                callbackText: String): QQZoneShareEntity {
            var entity = QQZoneShareEntity(QQZoneShareContentType.TYPE_PUBLISH_MOOD)
            entity.summary = summary
            entity.imageUrls = imageUrls
            entity.extraScene = extraScene
            entity.callbackText = callbackText
            return entity
        }

        /***
         * 生成发布视频说说数据类
         * @param summary 描述，必选
         * @param imageUrls 图片集合，可选
         * @param extraScene 标识，可选
         * @param callbackText 回调文字，可选
         * @return [QQZoneShareEntity]
         */
        @JvmStatic
        fun newPuslishVideo(
                summary: String,
                videoLocalPath: String = "",
                extraScene: String = "",
                callbackText: String): QQZoneShareEntity {
            var entity = QQZoneShareEntity(QQZoneShareContentType.TYPE_PUBLISH_VIDEO)
            entity.summary = summary
            entity.videoLocalPath = videoLocalPath
            entity.extraScene = extraScene
            entity.callbackText = callbackText
            return entity
        }

        /**
         * 创建QQ分享小程序对象
         * @param miniAppId QQ小程序appId，必填
         * @param miniAppVersionType QQ小程序版本类型[QQMiniProgramVersionType] 必填
         * @param miniAppPagePath QQ小程序页面路径，必填
         * @param webPageUrl QQ小程序网页链接，为兼容低版本而处理，必填
         * @param imageUrl String 封面图片链接，必填
         * @param title String 标题，可选
         * @param summary String 摘要，可选
         * @return QQZoneShareEntity
         */
        @JvmStatic
        fun newShareMiniProgram(miniAppId: String,
                                @QQMiniProgramVersionType
                                miniAppVersionType: Int,
                                miniAppPagePath: String,
                                webPageUrl: String,
                                imageUrl: String,
                                title: String = "",
                                summary: String = ""): QQZoneShareEntity {
            var entity = QQZoneShareEntity(QQShareContentType.TYPE_MINI_PROGRAM)
            entity.miniAppId = miniAppId
            entity.miniAppPagePath = miniAppPagePath
            entity.miniAppVersionType = miniAppVersionType
            entity.webPageUrl = webPageUrl
            entity.imageUrl = imageUrl
            entity.title = title
            entity.summary = summary
            return entity

        }

    }
}