package io.ganguo.wechat.annotation

import androidx.annotation.StringDef
import io.ganguo.wechat.annotation.WXShareContentType.Companion.TYPE_IMAGE
import io.ganguo.wechat.annotation.WXShareContentType.Companion.TYPE_MUSIC
import io.ganguo.wechat.annotation.WXShareContentType.Companion.TYPE_TEXT
import io.ganguo.wechat.annotation.WXShareContentType.Companion.TYPE_VIDEO
import io.ganguo.wechat.annotation.WXShareContentType.Companion.TYPE_WEB_PAGE

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 微信分享类型
 * </pre>
 * @property [TYPE_TEXT] 文字类型
 * @property [TYPE_MUSIC] 音乐类型
 * @property [TYPE_IMAGE] 图片类型
 * @property [TYPE_VIDEO] 视频类型
 * @property [TYPE_WEB_PAGE] 网页类型
 */
@StringDef(TYPE_TEXT, TYPE_IMAGE, TYPE_MUSIC, TYPE_VIDEO, TYPE_WEB_PAGE)
@Retention(AnnotationRetention.RUNTIME)
annotation class WXShareContentType {

    /**
     * @property TYPE_IMAGE 图片类型
     * @property TYPE_MINI_PROGRAM 微信小程序
     * @property TYPE_MUSIC 音乐类型
     * @property TYPE_TEXT 文字类型
     * @property TYPE_VIDEO 视频类型
     */
    companion object {
        const val TYPE_TEXT = "text"
        const val TYPE_IMAGE = "img"
        const val TYPE_MUSIC = "music"
        const val TYPE_VIDEO = "video"
        const val TYPE_WEB_PAGE = "webpage"
        const val TYPE_MINI_PROGRAM = "miniProgram"
    }
}