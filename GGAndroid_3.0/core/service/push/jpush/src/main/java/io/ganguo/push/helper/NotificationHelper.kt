package io.ganguo.push.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import io.ganguo.push.entity.PushEntity
import io.ganguo.utils.R
import io.ganguo.utils.helper.ResHelper
import io.ganguo.utils.util.Strings

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2019/09/26
 *     desc   : 通知工具类
 * </pre>
 */

class NotificationHelper {

    companion object {
        /**
         * intent传递[PushEntity]默认Key，可根据自己使用情况，自定义或者直接使用默认值
         */
        const val KEY_DATA = "data"
        /**
         * APP根目录路径，必须配置，否则[app根目录.NotificationReceiver]无法收到事件回调。(注意根目录不一定等于包名，请根据项目具体情况配置)
         */
        private const val APP_ROOT_PATH = "io.ganguo.demo"

        /**
         * 显示通知
         * @param context Context
         * @param entity PushEntity
         * @param dataKey String 传递数据Key
         */
        fun showNotification(context: Context, dataKey: String = KEY_DATA, entity: PushEntity) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var channel = NotificationChannel(context.packageName, "channel", NotificationManager.IMPORTANCE_LOW)
                notificationManager.createNotificationChannel(channel)
            }
            val builder = NotificationCompat.Builder(context, context.packageName)
            builder.setDefaults(Notification.DEFAULT_VIBRATE or Notification.DEFAULT_LIGHTS)
                    .setContentText(Strings.nullToEmpty(entity.title))
                    .setWhen(System.currentTimeMillis())
                    .setLights(Color.RED, 300, 1000)
                    .setAutoCancel(true)
                    .setSmallIcon(getIcon())
                    .setContentIntent(getNotifyIntent(context, dataKey, entity))
                    .setChannelId(context.packageName)
                    .setColor(ResHelper.getColor(R.color.colorPrimary))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            val notification = builder.build()
            notificationManager.notify(context.packageName, R.string.app_name, notification)
        }


        /**
         * 通知点击 Intent
         * @param context Context
         * @param dataKey String 传递数据Key
         * @return PendingIntent
         */
        private fun getNotifyIntent(context: Context, dataKey: String, entity: PushEntity): PendingIntent {
            check(APP_ROOT_PATH.isNotEmpty()) {
                "Please configure the NotificationHelper.APP_ROOT_PATH!!!"
            }
            val intent = Intent()
            intent.component = ComponentName(context.packageName, "${APP_ROOT_PATH}.receiver.NotificationReceiver")
            intent.putExtra(dataKey, entity)
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }


        /**
         * 清除通知
         * @param context Context
         */
        @JvmStatic
        fun clearAllNotification(context: Context) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.cancelAll()
        }

        /**
         * 通知显示 Icon
         * @return Int
         */
        @DrawableRes
        @JvmStatic
        private fun getIcon(): Int {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return R.drawable.ic_launcher
            }
            return R.drawable.ic_launcher
        }
    }
}