package io.ganguo.line.share

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import io.ganguo.line.LineHandler
import io.ganguo.line.annotation.LineShareContentType
import io.ganguo.sample.sdk.AShareService
import io.ganguo.sample.sdk.ShareResultObservable
import io.ganguo.sample.sdk.URIs
import io.reactivex.rxjava3.core.Observable
import java.lang.ref.WeakReference
import java.net.URLEncoder


/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/04
 *     desc   : Line分享服务
 * </pre>
 */
@Suppress("DEPRECATION")
class LineShareService(activity: Activity, private var shareEntity: LineShareEntity, private var listener: LineShareListener) : AShareService() {
    private var weakActivity: WeakReference<Activity>? = WeakReference(activity)

    /**
     * 发起分享服务
     * @return ShareResultObservable
     */
    override fun startService(): ShareResultObservable {
        try {
            weakActivity?.get()?.startActivity(buildShareIntent())
            listener.onComplete()
        } catch (e: Throwable) {
            e.printStackTrace()
            return Observable.error(Throwable(e.message.orEmpty(), e))
        }
        return resultObserver
    }

    /**
     * 检测Line SDK配置是否正确
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        return LineHandler.checkException(weakActivity?.get()!!)
    }


    /**
     * 构建分享Intent
     * @return
     */
    private fun buildShareIntent(): Intent {
        return when (shareEntity.type) {
            LineShareContentType.TYPE_IMAGE -> {
                newShareImageIntent()
            }
            LineShareContentType.TYPE_CONTENT -> {
                newShareContentIntent()
            }
            else -> {
                newShareWebPageIntent()
            }
        }
    }

    /**
     * 创建分享图片Intent
     * @return Intent
     */
    private fun newShareImageIntent(): Intent {
        var uri = Uri.parse(MediaStore.Images.Media.insertImage(weakActivity?.get()?.contentResolver, shareEntity.imageBitmap, null, null))
        val scheme = StringBuilder(LINE_ROUTE)
        scheme.append(formatTypeToIntentType())
        scheme.append(URIs.toAbsolutePath(weakActivity?.get()!!, uri))
        return Intent(Intent.ACTION_VIEW, Uri.parse(scheme.toString()))
    }

    /**
     * 创建分享网页链接Intent
     * @return Intent
     */
    private fun newShareWebPageIntent(): Intent {
        val stringBuilder = StringBuilder(LINE_ROUTE)
        stringBuilder.append(formatTypeToIntentType())
        if (shareEntity.title.isNotEmpty()) {
            stringBuilder.append(URLEncoder.encode(shareEntity.title + "\n", UTF_8))
        }
        if (shareEntity.webPageUrl.isNotEmpty()) {
            stringBuilder.append(URLEncoder.encode(shareEntity.webPageUrl + "\n", UTF_8))
        }
        return Intent(Intent.ACTION_VIEW, Uri.parse(stringBuilder.toString()))

    }

    /**
     * 创建文本分享Intent
     * @return Intent
     */
    private fun newShareContentIntent(): Intent {
        val share = StringBuilder(LINE_ROUTE)
        share.append(formatTypeToIntentType())
        if (shareEntity.title.isNotEmpty()) {
            share.append(URLEncoder.encode(shareEntity.title + "\n", UTF_8))
        }
        if (shareEntity.content.isNotEmpty()) {
            share.append(URLEncoder.encode(shareEntity.content + "\n", UTF_8))
        }
        return Intent(Intent.ACTION_VIEW, Uri.parse(share.toString()))
    }


    /**
     * [LineShareContentType] 转Intent Type
     * @return
     */
    private fun formatTypeToIntentType(): String {
        return when (shareEntity.type) {
            LineShareContentType.TYPE_IMAGE -> {
                "image"
            }
            LineShareContentType.TYPE_CONTENT -> {
                "text/"
            }
            else -> {
                "text/"
            }
        }
    }


    /**
     * @property [LINE_ROUTE] Line路由
     */
    companion object {
        const val LINE_ROUTE = "line://msg/"
        const val UTF_8 = "UTF-8"

    }

    override fun release() {
        super.release()
        weakActivity?.clear()
        weakActivity = null
    }

}