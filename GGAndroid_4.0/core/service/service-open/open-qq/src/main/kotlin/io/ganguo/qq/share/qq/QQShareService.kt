package io.ganguo.qq.share.qq

import android.app.Activity
import android.os.Bundle
import com.tencent.connect.share.QQShare
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import io.ganguo.sample.sdk.AShareService
import io.ganguo.sample.sdk.ShareResultObservable
import io.ganguo.qq.QQConstants
import io.ganguo.qq.QQHandler
import io.ganguo.qq.annotation.QQShareContentType
import io.ganguo.qq.entity.QQShareEntity
import io.reactivex.rxjava3.core.Observable
import java.lang.ref.WeakReference


/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : QQ分享
 * </pre>
 * @property callback 分享接口回调
 * @property weakActivity [WeakReference]Activity弱引用，避免内存泄露
 * @property appId QQ开放平台AppID
 * @property isRelease 资源是否释放标识
 */
class QQShareService(activity: Activity, var shareEntity: QQShareEntity, private var callback: IUiListener?) : AShareService() {
    private var weakActivity: WeakReference<Activity>? = WeakReference(activity)
    private var tencent: Tencent? = null

    init {
        tencent = Tencent.createInstance(QQConstants.QQ_APP_ID, weakActivity?.get())
    }


    /**
     * 生成 QQ 分享 Bundle
     *
     * @return [Bundle]
     */
    private fun buildShareBundle(): Bundle {
        return when (shareEntity.type) {
            QQShareContentType.TYPE_MINI_PROGRAM -> {
                newShareMiniProgramBundle()
            }
            QQShareContentType.TYPE_APP -> {
                newShareAppBundle()
            }
            QQShareContentType.TYPE_AUDIO -> {
                newShareMusicBundle()
            }
            QQShareContentType.TYPE_IMAGE -> {
                newShareImageBundle()
            }
            else -> {
                newShareWebPageBundle()
            }
        }
    }

    /**
     * 分享图文、网络网页链接数据对象
     * @return [Bundle]
     */
    private fun newShareWebPageBundle(): Bundle {
        var params = Bundle()
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareEntity.title)
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareEntity.summary)
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareEntity.webPageUrl)
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareEntity.imageUrl)
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareEntity.imageLocalPath)
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, shareEntity.appName)
        params.putString(QQShare.SHARE_TO_QQ_ARK_INFO, shareEntity.askInfo)
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, getOpenQZoneFlag())
        return params
    }


    /**
     * 分享图片数据对象
     * @return [Bundle]
     */
    private fun newShareImageBundle(): Bundle {
        var params = Bundle()
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE)
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareEntity.imageLocalPath)
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, shareEntity.appName)
        params.putString(QQShare.SHARE_TO_QQ_ARK_INFO, shareEntity.askInfo)
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, getOpenQZoneFlag())
        return params
    }

    /**
     * 分享音乐数据对象
     * @return [Bundle]
     */
    private fun newShareMusicBundle(): Bundle {
        var params = Bundle()
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO)
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareEntity.title)
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareEntity.summary)
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareEntity.webPageUrl)
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareEntity.imageUrl)
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareEntity.imageLocalPath)
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, shareEntity.audioUrl)
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, shareEntity.appName)
        params.putString(QQShare.SHARE_TO_QQ_ARK_INFO, shareEntity.askInfo)
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, getOpenQZoneFlag())
        return params
    }

    /**
     * 分享App数据对象
     * @return [Bundle]
     */
    private fun newShareAppBundle(): Bundle {
        var params = Bundle()
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP)
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareEntity.title)
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareEntity.summary)
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareEntity.imageUrl)
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareEntity.imageLocalPath)
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, shareEntity.appName)
        params.putString(QQShare.SHARE_TO_QQ_ARK_INFO, shareEntity.askInfo)
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, getOpenQZoneFlag())
        return params
    }

    /**
     * 分享QQ小程序数据对象
     * @return [Bundle]
     */
    private fun newShareMiniProgramBundle(): Bundle {
        val params = Bundle()
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_MINI_PROGRAM)
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareEntity.title)
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareEntity.summary)
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareEntity.webPageUrl)
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareEntity.imageUrl)
        params.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_APPID, shareEntity.miniAppId)
        params.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_PATH, shareEntity.miniAppPagePath)
        params.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_TYPE, shareEntity.miniAppVersionType.toString())
        return params
    }

    /**
     * 获取是否同时打开QQ空间分享标识
     * @return [Int]
     */
    private fun getOpenQZoneFlag(): Int {
        return if (shareEntity.isOpenQZone) {
            QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN
        } else {
            QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE
        }
    }


    /**
     * 调起QQ分享服务
     * @return [Observable]
     */
    override fun startService(): ShareResultObservable {
        tencent?.shareToQQ(weakActivity!!.get(), buildShareBundle(), callback)
        return resultObserver
    }

    /**
     * QQ SDK异常检测
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        return QQHandler.checkException(weakActivity?.get()!!)
    }


    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        tencent?.releaseResource()
        tencent = null
        weakActivity?.clear()
        weakActivity = null
        callback = null
    }

}