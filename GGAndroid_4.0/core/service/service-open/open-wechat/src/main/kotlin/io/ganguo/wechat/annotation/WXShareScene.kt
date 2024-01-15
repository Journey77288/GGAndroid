package io.ganguo.wechat.annotation

import androidx.annotation.IntDef
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import io.ganguo.wechat.annotation.WXShareScene.Companion.WX_SCENE_FAVORITE
import io.ganguo.wechat.annotation.WXShareScene.Companion.WX_SCENE_SESSION
import io.ganguo.wechat.annotation.WXShareScene.Companion.WX_SCENE_TIMELINE

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 微信分享渠道
 * </pre>
 * @property [WX_SCENE_FAVORITE] 微信收藏
 * @property [WX_SCENE_SESSION] 微信好友
 * @property [WX_SCENE_TIMELINE] 微信朋友圈
 */
@Retention(AnnotationRetention.BINARY)
@IntDef(WX_SCENE_SESSION, WX_SCENE_TIMELINE, WX_SCENE_FAVORITE)
annotation class WXShareScene {
    companion object {
        const val WX_SCENE_SESSION: Int = SendMessageToWX.Req.WXSceneSession
        const val WX_SCENE_TIMELINE: Int = SendMessageToWX.Req.WXSceneTimeline
        const val WX_SCENE_FAVORITE: Int = SendMessageToWX.Req.WXSceneFavorite
    }
}