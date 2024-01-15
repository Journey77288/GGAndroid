package io.ganguo.twitter.share

import android.app.Activity
import com.twitter.sdk.android.tweetcomposer.TweetComposer
import io.ganguo.open.sdk.ShareResultObservable
import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.annotation.OpenStatus
import io.ganguo.open.sdk.entity.OpenResult
import io.ganguo.twitter.ATwitterShareService
import io.ganguo.twitter.TwitterHandler
import io.ganguo.twitter.entity.TwitterShareEntity
import io.reactivex.Observable
import java.lang.ref.WeakReference
import java.net.URL

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/05
 *     desc   : Twitter 分享服务
 * </pre>
 */
class TwitterShareService(activity: Activity, private var shareEntity: TwitterShareEntity, var listener: TwitterShareListener) : ATwitterShareService() {
    private var weakActivity: WeakReference<Activity>? = WeakReference(activity)


    /**
     * 调用推特分享
     * @return ShareResultObservable
     */
    override fun startService(): ShareResultObservable {
        try {
            buildTweetBuilder().show()
            listener.onComplete()
        } catch (e: Throwable) {
            e.printStackTrace()
            return Observable.just(OpenResult(OpenChannel.TWITTER, OpenStatus.FAILED, e.message))
        }
        return resultObserver
    }

    /**
     * 检测推特SDK配置是否正常
     * @return Throwable?
     */
    override fun checkException(): Throwable? {
        return TwitterHandler.checkException()
    }


    /**
     * 创建分享数据对象
     * @return TweetComposer.Builder
     */
    private fun buildTweetBuilder(): TweetComposer.Builder {
        var builder = TweetComposer.Builder(weakActivity?.get()!!)
                .text(shareEntity.text);
        if (shareEntity.webPageUrl.isNotEmpty()) {
            builder.url(URL(shareEntity.webPageUrl))
        }
        if (shareEntity.imageFileUri != null) {
            builder!!.image(shareEntity.imageFileUri)
        }
        return builder
    }

    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        weakActivity?.clear()
        weakActivity = null
    }
}