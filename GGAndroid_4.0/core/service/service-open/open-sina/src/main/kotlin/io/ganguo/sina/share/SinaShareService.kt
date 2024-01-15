package io.ganguo.sina.share

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import com.sina.weibo.sdk.api.*
import com.sina.weibo.sdk.openapi.IWBAPI
import io.ganguo.sample.sdk.AShareService
import io.ganguo.sample.sdk.ShareResultObservable
import io.ganguo.sina.SinaHandler
import io.ganguo.sina.annotation.SinaShareContentType
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/14
 *     desc   : 新浪微信分享服务
 * </pre>
 */
class SinaShareService(private val activity: Activity, private var shareEntity: SinaShareEntity, shareHandler: IWBAPI?) : AShareService() {
    private var weakActivity: WeakReference<Activity>? = WeakReference(activity)
    private var weakShareHandler: WeakReference<IWBAPI?>? = WeakReference(shareHandler)


    /**
     * 生成新浪微博分享数据
     * @return WeiboMultiMessage
     */
    private fun buildWeiboMultiMessage(): WeiboMultiMessage {
        var msg: WeiboMultiMessage = WeiboMultiMessage()
        msg.textObject = newTextObject()
        when (shareEntity.type) {
            SinaShareContentType.TYPE_IMAGE -> {
                msg.imageObject = newImageObject()
            }
            SinaShareContentType.TYPE_VIDEO -> {
                msg.videoSourceObject = newVideoObject()
            }
            SinaShareContentType.TYPE_MULTI_IMAGE -> {
                msg.multiImageObject = newMultiImageObject()
            }
            SinaShareContentType.TYPE_WEB_PAGE -> {
                msg.mediaObject = newWebPageObject()
            }
            else -> {
                //TODO:什么也不做
            }
        }
        return msg
    }


    /**
     * 生成文字分享数据对象
     * @return
     */
    private fun newTextObject(): TextObject {
        val textObject = TextObject()
        textObject.text = shareEntity.text
        textObject.title = shareEntity.title
        return textObject
    }


    /**
     * 生成图片分享数据对象
     * @return ImageObject
     */
    private fun newImageObject(): ImageObject {
        var imageObject = ImageObject()
        imageObject.setImageData(shareEntity.imageBitmap)
        return imageObject
    }


    /**
     * 生成WebPage分享数据对象
     * @return WebPageObject
     */
    private fun newWebPageObject(): WebpageObject {
        var webpageObject = WebpageObject()
        webpageObject.identify = shareEntity.identify
        webpageObject.title = shareEntity.title
        webpageObject.defaultText = shareEntity.text
        webpageObject.description = shareEntity.description
        webpageObject.actionUrl = shareEntity.webPageUrl
        var os: ByteArrayOutputStream? = null
        try {
            os = ByteArrayOutputStream()
            shareEntity.thumbImageBitmap?.compress(Bitmap.CompressFormat.JPEG, 85, os)
            webpageObject.thumbData = os.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            os?.close()
        }
        return webpageObject
    }


    /**
     * 生成多图分享数据对象
     * @return MultiImageObject
     */
    private fun newMultiImageObject(): MultiImageObject {
        var imageObject = MultiImageObject()
        var pathUris = ArrayList<Uri>()
        shareEntity.imageLocalPaths
                .filter {
                    it.isNotEmpty() && File(it).exists()
                }
                .forEach {
                    pathUris.add(Uri.parse(it))
                }
        imageObject.imageList = pathUris
        return imageObject
    }


    /**
     * 生成视频分享数据对象
     * @return VideoSourceObject
     */
    private fun newVideoObject(): VideoSourceObject {
        var video = VideoSourceObject()
        video.videoPath = Uri.fromFile(File(shareEntity.videoLocalPath))
        return video
    }


    /**
     * 检测SDK或者App是否有异常
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        return SinaHandler.checkException(weakActivity?.get()!!)
    }

    /**
     * 启动新浪微博分享服务
     * @return ShareResultObservable
     */
    override fun startService(): ShareResultObservable {
        weakShareHandler?.get()?.shareMessage(activity, buildWeiboMultiMessage(), false)
        return resultObserver
    }

    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        weakActivity?.clear()
        weakActivity = null
        weakShareHandler?.clear()
        weakShareHandler = null
    }
}