package io.ganguo.qq.share.zone

import android.app.Activity
import android.os.Bundle
import com.tencent.connect.share.QQShare
import com.tencent.connect.share.QzonePublish
import com.tencent.connect.share.QzoneShare
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import io.ganguo.open.sdk.AShareService
import io.ganguo.open.sdk.ShareResultObservable
import io.ganguo.qq.QQConstants
import io.ganguo.qq.QQHandler
import io.ganguo.qq.annotation.QQZoneShareContentType
import io.ganguo.qq.entity.QQZoneShareEntity
import io.reactivex.Observable
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/02
 *     desc   :
 * </pre>
 */
class QQZoneShareService(activity: Activity, var shareEntity: QQZoneShareEntity, private var callback: IUiListener?) : AShareService() {
    private var weakActivity: WeakReference<Activity>? = WeakReference(activity)
    private var tencent: Tencent? = null

    init {
        tencent = Tencent.createInstance(QQConstants.QQ_APP_ID, weakActivity?.get())
    }


    /**
     * 调起QQ空间分享服务
     * @return ShareResultObservable
     */
    override fun startService(): ShareResultObservable {
        when (shareEntity.type) {
            QQZoneShareContentType.TYPE_PUBLISH_VIDEO -> {
                tencent?.publishToQzone(weakActivity?.get()!!, buildShareBundle(), callback)
            }
            else -> {
                tencent?.shareToQzone(weakActivity?.get()!!, buildShareBundle(), callback)
            }
        }
        return resultObserver
    }


    /**
     * QQ SDK异常检测
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        return QQHandler.checkException(weakActivity?.get()!!)
    }

    /***
     * 生成分享Bundle数据
     * @return [Bundle]
     */
    private fun buildShareBundle(): Bundle {
        return when (shareEntity.type) {
            QQZoneShareContentType.TYPE_WEB_PAGE -> {
                newWebPageBundle()
            }
            QQZoneShareContentType.TYPE_MINI_PROGRAM -> {
                newMiniProgramBundle()
            }
            else -> {
                newPublishBundle()
            }
        }
    }


    /***
     * 生成分享Bundle数据
     * @return [Bundle]
     */
    private fun newWebPageBundle(): Bundle {
        var params = Bundle()
        params.putString(QQShare.SHARE_TO_QQ_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT.toString())
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareEntity.title)
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareEntity.summary)
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareEntity.webPageUrl)
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, shareEntity.imageUrls)
        return params
    }

    /***
     * 生成QQ小程序分享Bundle数据
     * @return [Bundle]
     */
    private fun newMiniProgramBundle(): Bundle {
        val param = Bundle()
        param.putString(QQShare.SHARE_TO_QQ_TITLE, shareEntity.title)
        param.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareEntity.summary)
        param.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareEntity.webPageUrl)
        param.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareEntity.imageUrl)
        param.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_APPID, shareEntity.miniAppId)
        param.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_PATH, shareEntity.miniAppPagePath)
        param.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_TYPE, shareEntity.miniAppVersionType.toString())
        param.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_MINI_PROGRAM)
        return param
    }

    /***
     * 生成发布说说Bundle数据
     * @return [Bundle]
     */
    private fun newPublishBundle(): Bundle {
        var params = Bundle()
        params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareEntity.type.toString())
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareEntity.summary)
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, shareEntity.imageUrls)
        params.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, shareEntity.videoLocalPath)
        var extParams = Bundle()
        extParams.putString(QzonePublish.HULIAN_EXTRA_SCENE, shareEntity.extraScene)
        extParams.putString(QzonePublish.HULIAN_CALL_BACK, shareEntity.callbackText)
        params.putBundle(QzonePublish.PUBLISH_TO_QZONE_EXTMAP, extParams)
        return params
    }


    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        weakActivity?.clear()
        weakActivity = null
        tencent?.releaseResource()
        tencent = null
    }

}