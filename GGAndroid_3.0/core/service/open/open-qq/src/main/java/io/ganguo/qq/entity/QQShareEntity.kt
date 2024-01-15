package io.ganguo.qq.entity

import io.ganguo.qq.annotation.QQMiniProgramVersionType
import io.ganguo.qq.annotation.QQShareContentType

/**
 * <pre>
 * author : zoyen
 * time   : 2018/7/11
 * desc   : QQ分享数据
 * </pre>
 * @see [开发文档链接](https://wiki.connect.qq.com/%E5%88%86%E4%BA%AB%E6%B6%88%E6%81%AF%E5%88%B0qq%EF%BC%88%E6%97%A0%E9%9C%80qq%E7%99%BB%E5%BD%95%EF%BC%89)
 * @property [type] 分享类型 [QQShareContentType]
 * @property [imageUrl] 网络图片链接
 * @property [title] 分享标题
 * @property [webPageUrl]网页链接
 * @property [summary] 消息摘要
 * @property [audioUrl] 分享音乐文件链接
 * @property [appName] app名称
 * @property [imageLocalPath] 本地文件路径
 * @property [isOpenQZone] 是否同时打开QQ空间分享
 * @property [askInfo] 轻应用信息(json字符串)，可选字段，一般很少用到，需要时具体参考开发文档使用
 *
 */
data class QQShareEntity internal constructor(@QQShareContentType val type: Int) {
    var imageUrl: String? = ""
    var imageLocalPath: String? = ""
    var title: String? = ""
    var webPageUrl: String? = ""
    var summary: String? = ""
    var audioUrl: String? = ""
    var appName: String? = ""
    var isOpenQZone: Boolean = false
    var askInfo: String = ""
    @QQMiniProgramVersionType
    var miniAppVersionType: Int = QQMiniProgramVersionType.TYPE_RELEASE
    var miniAppPagePath: String = ""
    var miniAppId: String = ""

    companion object {


        /**
         * 创建QQ分享网页对象
         * @param webPageUrl String 网页链接，必填
         * @param title String 标题，必填
         * @param summary String 摘要，可选
         * @param imageUrl String 封面图片链接，可选
         * @param imageLocalPath String 本地图片路径，可选（和[imageUrl]字段只能二选一）
         * @param isOpenQZone Boolean 分享时，是否同时打开QQ空间分享，可选
         * @param appName app名称，可选
         * @param askInfo qq轻应用信息Json字符串，可选
         * @return QQShareEntity
         */
        @JvmStatic
        fun newShareWebPage(webPageUrl: String,
                            title: String,
                            appName: String = "",
                            summary: String = "",
                            imageUrl: String = "",
                            imageLocalPath: String = "",
                            askInfo: String = "",
                            isOpenQZone: Boolean = false): QQShareEntity {
            var entity = QQShareEntity(QQShareContentType.TYPE_WEB_PAGE)
            entity.webPageUrl = webPageUrl
            entity.title = title
            entity.summary = summary
            entity.imageUrl = imageUrl
            entity.imageLocalPath = imageLocalPath
            entity.appName = appName
            entity.isOpenQZone = isOpenQZone
            entity.askInfo = askInfo
            return entity
        }


        /**
         * 创建QQ分享图片对象
         * @param imageLocalPath String 本地图片路径，不支持网络图片路径，必选
         * @param isOpenQZone Boolean 分享时，是否同时打开QQ空间分享，可选
         * @param appName app名称，可选
         * @param askInfo qq轻应用信息Json字符串，可选
         * @return QQShareEntity
         */
        @JvmStatic
        fun newShareImage(imageLocalPath: String,
                          appName: String = "",
                          askInfo: String = "",
                          isOpenQZone: Boolean = false): QQShareEntity {
            var entity = QQShareEntity(QQShareContentType.TYPE_IMAGE)
            entity.imageLocalPath = imageLocalPath
            entity.appName = appName
            entity.isOpenQZone = isOpenQZone
            entity.askInfo = askInfo
            return entity
        }


        /**
         * 创建QQ分享音乐对象
         * @param webPageUrl String 网页链接，必填
         * @param audioUrl 音频资源链接
         * @param title String 标题，必填
         * @param summary String 摘要，可选
         * @param imageUrl String 封面图片链接，可选
         * @param imageLocalPath String 本地图片路径，可选（和[imageUrl]字段只能二选一）
         * @param isOpenQZone Boolean 分享时，是否同时打开QQ空间分享，可选
         * @param appName app名称，可选
         * @param askInfo qq轻应用信息Json字符串，可选
         * @return QQShareEntity
         */
        @JvmStatic
        fun newShareMusic(webPageUrl: String,
                          audioUrl: String,
                          title: String,
                          summary: String = "",
                          imageLocalPath: String = "",
                          imageUrl: String = "",
                          appName: String,
                          askInfo: String = "",
                          isOpenQZone: Boolean = false): QQShareEntity {
            var entity = QQShareEntity(QQShareContentType.TYPE_AUDIO)
            entity.webPageUrl = webPageUrl
            entity.audioUrl = audioUrl
            entity.title = title
            entity.summary = summary
            entity.imageLocalPath = imageLocalPath
            entity.imageUrl = imageUrl
            entity.appName = appName
            entity.isOpenQZone = isOpenQZone
            entity.askInfo = askInfo
            return entity
        }

        /**
         * 创建QQ分享App对象
         * @param title String 标题，必填
         * @param summary String 摘要，可选
         * @param imageUrl String 封面图片链接，可选
         * @param imageLocalPath String 本地图片路径，可选（和[imageUrl]字段只能二选一）
         * @param isOpenQZone Boolean 分享时，是否同时打开QQ空间分享，可选
         * @param appName app名称，可选
         * @param askInfo qq轻应用信息Json字符串，可选
         * @param askInfo qq轻应用信息Json字符串，可选
         * @return QQShareEntity
         */
        @JvmStatic
        fun newShareApp(title: String,
                        summary: String = "",
                        imageLocalPath: String,
                        imageUrl: String = "",
                        appName: String = "",
                        askInfo: String = "",
                        isOpenQZone: Boolean): QQShareEntity {
            var entity = QQShareEntity(QQShareContentType.TYPE_APP)
            entity.title = title
            entity.summary = summary
            entity.imageUrl = imageUrl
            entity.imageLocalPath = imageLocalPath
            entity.appName = appName
            entity.isOpenQZone = isOpenQZone
            entity.askInfo = askInfo
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
         * @return QQShareEntity
         */
        @JvmStatic
        fun newShareMiniProgram(miniAppId: String,
                                @QQMiniProgramVersionType
                                miniAppVersionType: Int,
                                miniAppPagePath: String,
                                webPageUrl: String,
                                imageUrl: String,
                                title: String = "",
                                summary: String = ""): QQShareEntity {
            var entity = QQShareEntity(QQShareContentType.TYPE_MINI_PROGRAM)
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