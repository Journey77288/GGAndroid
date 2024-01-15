package io.ganguo.qq.annotation

import androidx.annotation.IntDef
import com.tencent.connect.share.QQShare
import io.ganguo.qq.annotation.QQShareContentType.Companion.TYPE_MINI_PROGRAM
import io.ganguo.qq.annotation.QQShareContentType.Companion.TYPE_APP
import io.ganguo.qq.annotation.QQShareContentType.Companion.TYPE_AUDIO
import io.ganguo.qq.annotation.QQShareContentType.Companion.TYPE_WEB_PAGE
import io.ganguo.qq.annotation.QQShareContentType.Companion.TYPE_IMAGE

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : QQ内容分享类型
 * </pre>
 * @property TYPE_WEB_PAGE 图文分享(普通分享)
 * @property TYPE_IMAGE 分享纯图片
 * @property TYPE_AUDIO 分享音乐
 * @property TYPE_APP 分享应用
 * @property TYPE_MINI_PROGRAM 分享QQ小程序
 *
 */
@Retention(AnnotationRetention.BINARY)
@IntDef(TYPE_WEB_PAGE, TYPE_AUDIO, TYPE_IMAGE, TYPE_APP, TYPE_MINI_PROGRAM)
annotation class QQShareContentType {

    companion object {
        const val TYPE_WEB_PAGE = QQShare.SHARE_TO_QQ_TYPE_DEFAULT
        const val TYPE_AUDIO = QQShare.SHARE_TO_QQ_TYPE_AUDIO
        const val TYPE_IMAGE = QQShare.SHARE_TO_QQ_TYPE_IMAGE
        const val TYPE_APP = QQShare.SHARE_TO_QQ_TYPE_APP
        const val TYPE_MINI_PROGRAM = QQShare.SHARE_TO_QQ_MINI_PROGRAM
    }
}