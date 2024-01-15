package io.ganguo.rxbus

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.ConcurrentHashMap

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/18
 *   @desc   : RxBus (替代EventBus)
 * </pre>
 */
@Suppress("UNCHECKED_CAST")
class RxBus private constructor() {
    private val bus = PublishSubject.create<Any>().toSerialized()
    private val stickyEventContainers = ConcurrentHashMap<String, Any>()

    /**
     * 发送事件, 依靠[eventName]区分event type, 自动包装
     * @param eventData Any
     * @param eventName String
     */
    fun sendEvent(eventName: String, eventData: Any) {
        bus.onNext(RxEvent(eventName, eventData))
    }


    /**
     * receive event
     *
     * @param type
     * @param eventName
     * @return Observable<T>
     *
     *
     * 接收事件调用该方法
     * 转成 Observable, 只暴露receive event的data flow
     * type - 指定对应的event下, 过滤接收obj的类型
     * signal - 实际上是定义一个String来区分event type, 可以大大减少创建不同的event
    </T> */
    fun <T : Any> receiveEvent(eventName: String, type: Class<T>): Observable<T> {
        return bus.filter {
            isRxEvent(eventName, it, type)
        }.map { it ->
            (it as RxEvent).let {
                it.second as T
            }
        }
    }

    /**
     * 发送Sticky事件
     *
     * @param eventName
     * @param eventData
     *
     * ps.使用Sticky时，如果receive事件和send事件同时调用，必须确保receive的subScribeOn线程跟Send的线程要一致
     * 不然会出现注册之后无法接受到Sticky事件的问题,或者让send事件放在receive之前
     * (主要是因为判断map方法跟SubscribeOn的线程顺序可能不是map之后再执行subscribe)
     */
    fun sendStickyEvent(eventName: String, eventData: Any) {
        stickyEventContainers[eventName] = eventData
        sendEvent(eventName, eventData)
    }

    /**
     * 接收Sticky事件
     *
     * @param eventName
     * @param type
     * @return
     *
     * ps.使用Sticky时，如果receive事件和send事件同时调用，必须确保receive的subScribeOn线程跟Send的线程要一致
     * 不然会出现注册之后无法接受到Sticky事件的问题,或者让send事件放在receive之前
     * (主要是因为判断map方法跟SubscribeOn的线程顺序可能不是map之后再执行subscribe)
     */
    fun <T : Any> receiveStickyEvent(eventName: String, type: Class<T>): Observable<T> {
        val receiver = receiveEvent(eventName, type)
        val obj = stickyEventContainers[eventName]
        // 如果有保存的sticky时间，则key能获取到obj
        if (obj != null && type.isInstance(obj)) {
            return receiver.mergeWith(Observable.create {
                try {
                    // 相当于绑定了一个cold event，
                    // 只要有新的订阅希望接收这个类型的sticky event就会触发,
                    // 而另外一个hot event(即正常receiver)不会受到影响
                    it.onNext(obj as T)
                } catch (e: Exception) {
                    it.onError(e)
                } finally {
                    it.onComplete()
                }
            })
        } else {
            // 否则直接返回普通的receiver
            return receiver
        }
    }

    /**
     * 移除某个sticky事件
     *
     * @param eventName
     * @return
     */
    fun removeStickyEvent(eventName: String): Boolean {
        return stickyEventContainers.remove(eventName) != null
    }

    /**
     * 移除所有sticky事件
     */
    fun removeAllStickEvent() {
        stickyEventContainers.clear()
    }

    /**
     * Judge whether obj is RxEvent
     * @param data Any
     * @param type Class<T>
     * @param eventName String
     * @return Boolean
     */
    private fun <T> isRxEvent(eventName: String, data: Any, type: Class<T>) = let {
        if (!RxEvent::class.java.isInstance(data)) {
            false
        } else {
            val event = data as RxEvent
            type.isInstance(event.second) && eventName == event.first
        }
    }


    companion object {
        private val instance: RxBus by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RxBus()
        }

        /**
         * RxBus instance
         * @return RxBus
         */
        fun get(): RxBus {
            return instance
        }
    }
}
