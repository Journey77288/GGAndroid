package io.ganguo.twitter.share

import android.app.Activity
import io.ganguo.twitter.ITwitterShareProvider
import io.ganguo.twitter.entity.TwitterShareEntity

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/05
 *     desc   : Twitter分享服务创建
 * </pre>
 */
class TwitterShareProvider(var activity: Activity, var shareEntity: TwitterShareEntity, var listener: TwitterShareListener) : ITwitterShareProvider {
    /**
     *  创建Twitter分享服务
     * @return TwitterShareService
     */
    override fun newService(): TwitterShareService {
        return TwitterShareService(activity, shareEntity, listener)
    }
}