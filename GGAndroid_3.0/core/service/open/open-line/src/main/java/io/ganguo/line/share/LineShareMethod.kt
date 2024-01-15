package io.ganguo.line.share

import android.app.Activity
import io.ganguo.line.ALineShareMethod
import io.ganguo.open.sdk.annotation.OpenChannel
import io.ganguo.open.sdk.annotation.OpenStatus
import io.ganguo.open.sdk.entity.OpenResult

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/04
 *     desc   : line分享
 * </pre>
 */
class LineShareMethod : ALineShareMethod(), LineShareListener {


    /**
     * 创建服务提供者Provider
     * @param activity
     * @return [LineShareProvider]
     */
    override fun newShareProvider(activity: Activity, shareParam: LineShareEntity): LineShareProvider {
        return LineShareProvider(activity, shareParam, this)
    }

    /**
     * Line分享是通过调用系统Intent进行分享，所以没有成功/取消回调，一般是成功回调
     */
    override fun onComplete() {
        sendResult(OpenResult(OpenChannel.LINE, OpenStatus.SUCCESS))
    }

    /**
     * 分享失败
     */
    override fun onFailed(e: Exception) {
        sendResult(OpenResult(OpenChannel.LINE, OpenStatus.FAILED, e.message))
    }


}