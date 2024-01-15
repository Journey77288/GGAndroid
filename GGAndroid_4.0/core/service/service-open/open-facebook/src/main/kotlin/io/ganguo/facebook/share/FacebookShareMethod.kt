package io.ganguo.facebook.share

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.share.Sharer
import io.ganguo.facebook.AFBShareMethod
import io.ganguo.facebook.FacebookConstants
import io.ganguo.facebook.entity.FacebookShareEntity
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.annotation.OpenStatus
import io.ganguo.sample.sdk.entity.OpenResult

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/01
 *     desc   : Facebook分享
 * </pre>
 */
@Suppress("DEPRECATION")
class FacebookShareMethod(context: Application, appId: String) : AFBShareMethod(), FacebookCallback<Sharer.Result> {

    init {
        FacebookSdk.setApplicationId(appId)
        FacebookSdk.sdkInitialize(context)
        AppEventsLogger.activateApp(context)
        FacebookConstants.FACEBOOK_SDK_IS_INIT = true
    }

    /**
     * FacebookShareService 创建方法
     * @param shareParam 分享参数
     * @param activity activity引用
     * @return
     */
    override fun newShareProvider(activity: FragmentActivity, shareParam: FacebookShareEntity): FacebookShareProvider {
        return FacebookShareProvider(activity, shareParam, this)
    }

    /**
     * 分享成功
     * @param result Result
     */
    override fun onSuccess(result: Sharer.Result) {
        sendResult(OpenResult(OpenChannel.FACE_BOOK, OpenStatus.SUCCESS, result = result))
    }

    /**
     * 分享取消
     */
    override fun onCancel() {
        sendResult(OpenResult(OpenChannel.FACE_BOOK, OpenStatus.CANCEL))
    }

    /**
     * 分享失败
     */
    override fun onError(error: FacebookException) {
        sendResult(OpenResult(OpenChannel.FACE_BOOK, OpenStatus.FAILED, message = error?.message))
    }


}
