package io.ganguo.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/12/09
 *     desc   : 通知相关工具类
 * </pre>
 */
object Notifications {

    /**
     * 判断应用消息权限是否已打开
     *
     * @param context Context
     * @return Boolean
     */
    fun isAppNotificationEnabled(context: Context): Boolean {
        val managerCompat = NotificationManagerCompat.from(context)
        return managerCompat.areNotificationsEnabled()
    }

    /**
     * 打开应用通知权限设置页面
     *
     * @param context Context
     */
    fun openAppNotification(context: Context) {
        val packageName = context.packageName
        val intent: Intent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val uid = context.applicationInfo.uid
            intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            // 适配Android 8.0 以上的通知传参
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, uid)
            // 适配Android 5.0 - Android 7.1的通知传参
            intent.putExtra("app_package", packageName)
            intent.putExtra("app_uid", uid)
        } else {
            intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
        }
        context.startActivity(intent)
    }
}