package io.ganguo.factory.method

import android.app.Activity
import io.ganguo.factory.GGFactory
import io.ganguo.factory.IProvider
import io.ganguo.factory.IWeakReference
import io.ganguo.factory.result.IRxResultSend
import io.ganguo.factory.service.ResultEmitterService
import io.reactivex.rxjava3.subjects.Subject
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/14
 *     desc   : Method 基类
 * </pre>
 * @property  weak Activity弱引用
 * @property resultEmitter 结果发送Subject对象
 * @property isRelease 资源是否释放
 */
abstract class Method<Result, Observer, Service : ResultEmitterService<Result, Observer>, Provider : IProvider<Service>> :
        AbstractMethod<Service, Provider>(),
        IRxResultSend<Result>,
        IWeakReference<Activity> {
    lateinit var service: Service
    override var weak: WeakReference<Activity>? = null
    override var resultEmitter: Subject<Result>? = null


    /**
     * 获取一个Service服务
     * @param activity activity引用
     * @param provider Service 创建接口实现
     * @return [Service]
     */
    override fun asService(activity: Activity, provider: Provider): Service {
        resetRelease()
        service = GGFactory.newService(provider)
        resultEmitter = service.resultSubject
        return service
    }


    /**
     * 资源释放
     */
    override fun release() {
        super.release()
        resetRelease()
        GGFactory.removeService(service)
        weak?.clear()
        weak = null
        resultEmitter = null
    }

    /**
     * 发送结果
     * @param data Result
     */
    override fun sendResult(data: Result) {
        super.sendResult(data)
        release()
    }

}