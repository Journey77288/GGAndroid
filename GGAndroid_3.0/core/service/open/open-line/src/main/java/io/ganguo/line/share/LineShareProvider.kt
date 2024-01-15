package io.ganguo.line.share

import android.app.Activity
import io.ganguo.line.ILineShareProvider

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/04
 *     desc   : Line分享服务创建类
 * </pre>
 */
class LineShareProvider(var activity: Activity, var shareEntity: LineShareEntity, var listener: LineShareListener) : ILineShareProvider {

    override fun newService(): LineShareService {
        return LineShareService(activity, shareEntity, listener)
    }
}