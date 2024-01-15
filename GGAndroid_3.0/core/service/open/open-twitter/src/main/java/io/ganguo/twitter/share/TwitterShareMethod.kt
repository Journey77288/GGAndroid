package io.ganguo.twitter.share

import android.app.Activity
import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.annotation.OpenStatus
import io.ganguo.open.sdk.entity.OpenResult
import io.ganguo.twitter.ATwitterShareMethod
import io.ganguo.twitter.TwitterHandler
import io.ganguo.twitter.entity.TwitterShareEntity

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/05
 *     desc   : Twitter 分享
 * </pre>
 */
class TwitterShareMethod : ATwitterShareMethod(), TwitterShareListener {


    /**
     * 创建服务提供者Provider
     * @param activity
     * @param p 分享数据
     * @return [TwitterShareProvider]
     */
    override fun newShareProvider(activity: Activity, shareParam: TwitterShareEntity): TwitterShareProvider {
        return TwitterShareProvider(activity, shareParam, this)
    }

    /**
     * Twitter目前分享方式，一般情况下，只会有分享成功回调
     */
    override fun onComplete() {
        sendResult(OpenResult(OpenChannel.TWITTER, OpenStatus.SUCCESS))
    }

    /**
     * Twitter分享失败回调，一般是sdk调用失败
     */
    override fun onFailed(exception: Throwable) {
        sendResult(OpenResult(OpenChannel.TWITTER, OpenStatus.FAILED, message = exception.message))
    }


}