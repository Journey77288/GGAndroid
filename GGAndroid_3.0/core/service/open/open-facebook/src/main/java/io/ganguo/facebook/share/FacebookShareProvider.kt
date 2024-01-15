package io.ganguo.facebook.share

import android.app.Activity
import com.facebook.FacebookCallback
import com.facebook.share.Sharer
import io.ganguo.facebook.IFBShareProvider
import io.ganguo.facebook.entity.FacebookShareEntity
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/01
 *     desc   : Facebook分享服务创建类
 * </pre>
 */
class FacebookShareProvider(activity: Activity, var shareEntity: FacebookShareEntity, var callback: FacebookCallback<Sharer.Result>) : IFBShareProvider {
    private var weakActivity = WeakReference<Activity>(activity)

    override fun newService(): FacebookShareService {
        return FacebookShareService(weakActivity.get()!!, shareEntity, callback)
    }
}