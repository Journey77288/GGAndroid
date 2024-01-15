package io.ganguo.qq.annotation

import com.tencent.connect.share.QQShare
import com.tencent.connect.share.QzonePublish
import com.tencent.connect.share.QzoneShare

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/02
 *     desc   : QQ空间分享类型
 * </pre>
 * @property [TYPE_WEB_PAGE] 分享网页到QQ空间
 * @property [TYPE_PUBLISH_MOOD] 发布图文说说
 * @property [TYPE_PUBLISH_VIDEO] 发布视频说说
 * @property [TYPE_MINI_PROGRAM] 分享QQ小程序到QQ空间
 */
annotation class QQZoneShareContentType {
    companion object {
        const val TYPE_WEB_PAGE = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT
        const val TYPE_PUBLISH_MOOD = QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD
        const val TYPE_PUBLISH_VIDEO = QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO
        const val TYPE_MINI_PROGRAM = QQShare.SHARE_TO_QQ_MINI_PROGRAM
    }
}