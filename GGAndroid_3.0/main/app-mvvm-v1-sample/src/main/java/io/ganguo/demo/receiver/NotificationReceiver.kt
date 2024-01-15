package io.ganguo.demo.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.ganguo.push.entity.PushEntity
import io.ganguo.push.helper.NotificationHelper
import io.ganguo.utils.helper.ToastHelper

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2019/10/10
 *     desc   :
 * </pre>
 */

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var entity = intent.getParcelableExtra<PushEntity>(NotificationHelper.KEY_DATA)
        ToastHelper.showMessage("$entity")
    }
}