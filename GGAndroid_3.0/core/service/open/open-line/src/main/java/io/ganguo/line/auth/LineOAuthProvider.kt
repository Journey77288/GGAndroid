package io.ganguo.line.auth

import android.app.Activity
import io.ganguo.line.ILineOAuthProvider

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/04
 *     desc   : 服务创建类
 * </pre>
 */
class LineOAuthProvider(var activity: Activity, var appChannelId: String) : ILineOAuthProvider {

    override fun newService(): LineOAuthService {
        return LineOAuthService(activity, appChannelId)
    }
}