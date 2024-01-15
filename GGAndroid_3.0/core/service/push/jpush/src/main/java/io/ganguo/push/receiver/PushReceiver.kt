package io.ganguo.push.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cn.jpush.android.api.JPushInterface
import io.ganguo.push.entity.PushEntity
import io.ganguo.push.manager.PushManager
import io.ganguo.utils.util.Strings


/**
 * <pre>
 *     @author : zoyen
 *     time   : 2019/09/25
 *     desc   : 自定义消息的广播接收器
 * </pre>
 */

class PushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var bundle = intent.extras
        var action = intent.action
        if (Strings.isEquals(JPushInterface.ACTION_MESSAGE_RECEIVED, action)) {
            val message = bundle?.getString(JPushInterface.EXTRA_MESSAGE)
            PushManager.get().receiverPush(context, PushEntity(title = message))
        }
    }
}