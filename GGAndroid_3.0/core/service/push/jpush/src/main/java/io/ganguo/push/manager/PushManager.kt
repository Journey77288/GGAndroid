package io.ganguo.push.manager

import android.app.Application
import android.content.Context
import cn.jpush.android.api.JPushInterface
import io.ganguo.push.entity.PushEntity
import io.ganguo.push.helper.NotificationHelper
import io.ganguo.utils.helper.LanguageHelper
import io.ganguo.utils.util.Strings
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import java.lang.ref.WeakReference

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2019/09/25
 *     desc   : 推送管理工具类
 * </pre>
 * @property registerEmitter 注册别名生成的发射器，用于回调别名注册成功状态
 * @property unregisterEmitter 删除别名生成的发射器，用于回调删除别名成功状态
 * @property pushEmitter 推送发射器，当 主程序 通过 receiveEvent 订阅后，通过 pushEmitter 接受推送信息 (pushEmitter 不活跃时，则直接显示通过)
 */
class PushManager private constructor() {
    private var registerEmitter: ObservableEmitter<Boolean>? = null
    private var unregisterEmitter: ObservableEmitter<Boolean>? = null
    private var pushEmitter: ObservableEmitter<PushEntity>? = null

    /**
     * 更新别名状态
     * @param value String
     */
    internal fun updateAlisa(value: String?) {
        if (Strings.isNotEmpty(value)) {
            registerEmitter?.onNext(true)
            registerEmitter?.onComplete()
        } else {
            unregisterEmitter?.onNext(true)
            unregisterEmitter?.onComplete()
        }
    }

    /**
     * 设置别名
     * @param id String
     * @return Observable<Boolean>
     */
    fun setAlias(id: String): Observable<Boolean> {
        var observable = Observable.create<Boolean> {
            registerEmitter = it
        }
        JPushInterface.setAlias(application, 1, id)
        return observable
    }

    /**
     * 删除别名
     * @return Observable<Boolean>
     */
    fun deleteAlias(): Observable<Boolean> {
        var observable = Observable.create<Boolean> {
            unregisterEmitter = it
        }
        JPushInterface.deleteAlias(application, 1)
        return observable
    }

    /**
     * 订阅推送信息
     * @return Observable<PushEntity>
     */
    fun observePushEvent(): Observable<PushEntity> {
        return Observable.create {
            pushEmitter = it
        }
    }

    /**
     * 处理推送
     * 当 pushEmitter 处于活跃状态时，通过 onNext 分发；否则直接显示通知
     * @param context Context
     * @param entity PushEntity
     */
    internal fun receiverPush(context: Context, entity: PushEntity) {
        if (pushEmitter == null || pushEmitter?.isDisposed == true) {
            NotificationHelper.showNotification(context, entity = entity)
        } else {
            pushEmitter?.onNext(entity)
        }
    }


    companion object {
        private lateinit var instance: PushManager
        private lateinit var application: Application

        /**
         * 单例 懒加载模式
         * @return
         */
        fun get(): PushManager {
            return instance
        }

        /**
         * 初始化
         * @param context Context
         */
        @JvmStatic
        fun initialize(context: Application, debug: Boolean) {
            check(!::instance.isInitialized) { "Already initialized" }
            this.application = context
            this.instance = PushManager()
            JPushInterface.setDebugMode(debug)
            JPushInterface.init(context)
        }
    }
}

