package io.ganguo.open.sdk.annotation

import androidx.annotation.StringDef
import io.ganguo.open.sdk.annotation.OpenChannel.Companion.ALI_PAY
import io.ganguo.open.sdk.annotation.OpenChannel.Companion.FACE_BOOK
import io.ganguo.open.sdk.annotation.OpenChannel.Companion.GOOGLE
import io.ganguo.open.sdk.annotation.OpenChannel.Companion.LINE
import io.ganguo.open.sdk.annotation.OpenChannel.Companion.QQ
import io.ganguo.open.sdk.annotation.OpenChannel.Companion.QQ_ZONE
import io.ganguo.open.sdk.annotation.OpenChannel.Companion.SINA
import io.ganguo.open.sdk.annotation.OpenChannel.Companion.TWITTER
import io.ganguo.open.sdk.annotation.OpenChannel.Companion.WE_CHAT

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 登录/认证渠道
 * </pre>
 * @property QQ qq登录
 * @property QQ_ZONE QQ控件
 * @property WE_CHAT 微信
 * @property TWITTER 推特
 * @property SINA 新浪微博
 * @property GOOGLE Google
 * @property FACE_BOOK facebook
 * @property ALI_PAY 支付宝
 * @property LINE line
 */
@Retention(AnnotationRetention.SOURCE)
@StringDef(QQ, QQ_ZONE, WE_CHAT, TWITTER, SINA, GOOGLE, FACE_BOOK, ALI_PAY, LINE)
annotation class OpenChannel {
    companion object {
        const val QQ: String = "qq"
        const val QQ_ZONE: String = "qq_zone"
        const val WE_CHAT: String = "we_chat"
        const val TWITTER: String = "twitter"
        const val SINA: String = "sina"
        const val GOOGLE: String = "google"
        const val FACE_BOOK: String = "face_book"
        const val ALI_PAY: String = "aliPay"
        const val LINE: String = "line"
    }

}