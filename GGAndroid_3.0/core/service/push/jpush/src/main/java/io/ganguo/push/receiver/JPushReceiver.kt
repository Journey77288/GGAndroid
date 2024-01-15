package io.ganguo.push.receiver

import android.content.Context
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.service.JPushMessageReceiver
import io.ganguo.push.manager.PushManager

/**
 * <pre>
 *     @author : zoyen
 *     time   :  2019/09/25
 *     desc   : 自定义的广播接收器
 *              极光推送 3.3.0开始是通过继承 JPushMessageReceiver并配置来接收所有事件回调
 * </pre>
 */

class JPushReceiver : JPushMessageReceiver() {
    override fun onAliasOperatorResult(p0: Context?, p1: JPushMessage) {
        super.onAliasOperatorResult(p0, p1)
        PushManager.get().updateAlisa(p1.alias)
    }
}