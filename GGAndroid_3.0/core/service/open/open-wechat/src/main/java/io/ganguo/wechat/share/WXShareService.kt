package io.ganguo.wechat.share

import android.app.Activity
import com.tencent.mm.opensdk.modelmsg.*
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.ganguo.open.sdk.AShareService
import io.ganguo.open.sdk.OpenHelper
import io.ganguo.open.sdk.ShareResultObservable
import io.ganguo.wechat.WXHandler
import io.ganguo.wechat.annotation.WXShareContentType
import java.lang.ref.WeakReference


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 微信分享
 * </pre>
 */
class WXShareService(activity: Activity, appId: String, var shareEntity: WXShareEntity) : AShareService() {
    private var weakActivity: WeakReference<Activity>? = WeakReference(activity)
    private var iWxapi: IWXAPI? = null


    init {
        this.iWxapi = WXAPIFactory.createWXAPI(weakActivity!!.get(), appId, true)
    }


    /**
     * 调用微信分享
     * @return [ShareResultObservable]
     */
    override fun startService(): ShareResultObservable {
        val req = SendMessageToWX.Req()
        req.transaction = OpenHelper.buildTransaction(shareEntity.type)
        req.message = buildShareMediaMessage()
        req.scene = shareEntity.scene
        this.iWxapi!!.sendReq(req)
        return resultObserver
    }


    /**
     * 检测微信SDK配置是否正确
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        return WXHandler.checkException(weakActivity?.get()!!)
    }


    /**
     * 创建分享Object
     *
     * @param shareData 分享数据类
     * @return [WXMediaMessage.IMediaObject]
     */
    private fun buildShareMediaMessage(): WXMediaMessage {
        return when (shareEntity.type) {
            WXShareContentType.TYPE_WEB_PAGE -> {
                newWebPageMediaObject()
            }
            WXShareContentType.TYPE_MUSIC -> {
                newMusicMediaMessage()
            }
            WXShareContentType.TYPE_TEXT -> {
                newTextMediaMessage()
            }
            WXShareContentType.TYPE_VIDEO -> {
                newVideoMediaMessage()
            }
            WXShareContentType.TYPE_MINI_PROGRAM -> {
                newMiniProgramMediaMessage()
            }
            else -> {
                newImageMediaMessage()
            }
        }
    }

    /**
     * 微信小程序分享对象
     * @return WXMediaMessage
     */
    private fun newMiniProgramMediaMessage(): WXMediaMessage {
        var miniApp = WXMiniProgramObject()
        miniApp.webpageUrl = shareEntity.webPageUrl
        miniApp.miniprogramType = shareEntity.miniAppVersionType
        miniApp.path = shareEntity.miniAppPagePath
        miniApp.userName = shareEntity.miniAppId
        var message = WXMediaMessage(miniApp)
        message.title = shareEntity.title
        message.description = shareEntity.description
        message.thumbData = shareEntity.thumbImageBitmapByteArray
        return message
    }


    /**
     * 网页链接分享对象
     * @return WXMediaMessage
     */
    private fun newWebPageMediaObject(): WXMediaMessage {
        var webPage = WXWebpageObject()
        webPage.webpageUrl = shareEntity.webPageUrl
        var message = WXMediaMessage(webPage)
        message.title = shareEntity.title
        message.description = shareEntity.description
        message.thumbData = shareEntity.thumbImageBitmapByteArray
        return message
    }

    /**
     * 文本分享对象
     * @return WXMediaMessage
     */
    private fun newTextMediaMessage(): WXMediaMessage {
        var text = WXTextObject()
        text.text = shareEntity.text
        var message = WXMediaMessage(text)
        message.description = shareEntity.text
        return message
    }

    /**
     * 图片分享对象
     * @return WXMediaMessage
     */
    private fun newImageMediaMessage(): WXMediaMessage {
        var image = WXImageObject(shareEntity.imageBitmap)
        var message = WXMediaMessage(image)
        message.thumbData = shareEntity.thumbImageBitmapByteArray
        return message

    }

    /**
     * 音乐分享对象
     * @return WXMediaMessage
     */
    private fun newMusicMediaMessage(): WXMediaMessage {
        var music = WXMusicObject()
        music.musicUrl = shareEntity.musicUrl
        music.musicLowBandUrl = shareEntity.musicLowBandUrl
        music.musicDataUrl = shareEntity.musicDataUrl
        music.musicLowBandDataUrl = shareEntity.musicLowBandDataUrl
        var message = WXMediaMessage(music)
        message.title = shareEntity.title
        message.description = shareEntity.description
        message.thumbData = shareEntity.thumbImageBitmapByteArray
        return message
    }

    /**
     * 视频分享对象
     * @return WXMediaMessage
     */
    private fun newVideoMediaMessage(): WXMediaMessage {
        var video = WXVideoObject()
        video.videoUrl = shareEntity.videoUrl
        video.videoLowBandUrl = shareEntity.videoLowBandUrl
        var message = WXMediaMessage(video)
        message.title = shareEntity.title
        message.description = shareEntity.description
        message.thumbData = shareEntity.thumbImageBitmapByteArray
        return message
    }


    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        iWxapi?.detach()
        iWxapi = null
        weakActivity?.clear()
        weakActivity = null
    }
}

