package io.ganguo.facebook.share

import android.app.Activity
import android.net.Uri
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.share.Sharer
import com.facebook.share.model.*
import com.facebook.share.widget.ShareDialog
import io.ganguo.facebook.AFBShareService
import io.ganguo.facebook.FBShareResultObservable
import io.ganguo.facebook.FacebookConstants
import io.ganguo.facebook.FacebookHandler
import io.ganguo.facebook.annotation.FBShareContentType
import io.ganguo.facebook.entity.FacebookShareEntity
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/01
 *     desc   : Facebook分享服务
 * </pre>
 */
class FacebookShareService(activity: Activity, var shareEntity: FacebookShareEntity, var callback: FacebookCallback<Sharer.Result>) : AFBShareService() {
    private var weakActivity: WeakReference<Activity>? = WeakReference(activity)

    init {
        FacebookConstants.callbackManager = CallbackManager.Factory.create()
    }


    /**
     * 调起FaceBook分享服务
     * @return FBShareResultObservable
     */
    override fun startService(): FBShareResultObservable {
        var shareDialog = ShareDialog(weakActivity!!.get())
        shareDialog.registerCallback(FacebookConstants.callbackManager, callback)
        shareDialog.show(buildShareContent())
        return resultObserver
    }


    /**
     * 检测Facebook SDK 配置是否正常
     * @return
     */
    override fun checkException(): Throwable? {
        return FacebookHandler.checkException()
    }

    /**
     * 获取分享内容对象
     * @return FBShareResultObservable
     */
    private fun buildShareContent(): ShareContent<*, *> {
        return when (shareEntity.type) {
            FBShareContentType.TYPE_WEB_PAGE -> {
                newShareWebPageContent()
            }
            FBShareContentType.TYPE_MEDIA -> {
                newShareMediaContent()
            }
            FBShareContentType.TYPE_IMAGE_LOCAL_PATH, FBShareContentType.TYPE_IMAGE_BITMAP, FBShareContentType.TYPE_IMAGE_URL -> {
                newShareImageContent()
            }
            else -> {
                newShareVideoContent()
            }
        }
    }


    /**
     * 创建分享网页链接数据对象
     * @return ShareLinkContent
     */
    private fun newShareWebPageContent(): ShareLinkContent {
        return ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(shareEntity.webPageUrl))
                .setQuote(shareEntity.quote)
                .build()
    }

    /**
     * 创建视频分享数据对象
     * @return ShareLinkContent
     */
    private fun newShareVideoContent(): ShareVideoContent {
        var video = ShareVideo
                .Builder()
                .setLocalUrl(Uri.parse(shareEntity.videoLocalPath))
                .build()
        var photo = SharePhoto
                .Builder()
                .setBitmap(shareEntity.videoThumbBitmap)
                .build()
        return ShareVideoContent
                .Builder()
                .setContentTitle(shareEntity.title)
                .setPreviewPhoto(photo)
                .setContentDescription(shareEntity.description)
                .setVideo(video)
                .build()
    }


    /**
     * 创建图片分享数据对象
     * @return ShareLinkContent
     */
    private fun newShareImageContent(): SharePhotoContent {
        var photo = SharePhoto.Builder()
        photo.setCaption(shareEntity.caption)
        photo.setUserGenerated(shareEntity.isUserGenerated)
        when {
            FBShareContentType.TYPE_IMAGE_BITMAP == shareEntity.type -> photo.setBitmap(shareEntity.imageBitmap)
            FBShareContentType.TYPE_IMAGE_URL == shareEntity.type -> photo.setImageUrl(Uri.parse(shareEntity.imageUrl))
            else -> photo.setImageUrl(Uri.parse(shareEntity.imageLocalPath))
        }
        return SharePhotoContent.Builder()
                .addPhoto(photo.build())
                .build()
    }

    /**
     * 创建媒体分享内容
     * @return ShareLinkContent
     */
    private fun newShareMediaContent(): ShareMediaContent {
        var builder = ShareMediaContent.Builder()
        shareEntity.imageLocalPaths.forEach {
            builder.addMedium(SharePhoto
                    .Builder()
                    .setImageUrl(Uri.parse(it))
                    .build())
        }
        shareEntity.videoLocalPaths.forEach {
            builder.addMedium(ShareVideo
                    .Builder()
                    .setLocalUrl(Uri.parse(it))
                    .build())
        }
        return builder
                .build()
    }

    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        weakActivity?.clear()
        weakActivity = null
        FacebookConstants.callbackManager = null
    }
}