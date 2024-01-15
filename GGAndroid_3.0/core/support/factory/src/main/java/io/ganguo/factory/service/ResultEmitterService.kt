package io.ganguo.factory.service

import io.ganguo.factory.result.IRxResultEmitter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/14
 *     desc   : ResultEmitterService
 * </pre>
 * @property [Result] 返回结果类型
 * @property [Observer] Observer对象，用于订阅操作时间返回结果
 * @property [isRelease] 用于判断资源是否释放
 */
@Suppress("UNCHECKED_CAST")
abstract class ResultEmitterService<Result, Observer> : IRxResultEmitter<Subject<Result>, Observer>, IService {
    override var isRelease: Boolean = false
    override val resultSubject: Subject<Result> by lazy {
        PublishSubject.create<Result>().toSerialized()
    }
    override val resultObserver: Observer by lazy {
        resultSubject.hide() as Observer
    }


    /**
     * 发射异常或者启动服务
     * @return Observer
     */
    protected fun sendErrorOrStartService(): Observer {
        val error = checkException()
        return if (error != null) {
            Observable.error<Result>(error) as Observer
        } else {
            startService()
        }
    }


    /**
     * 启动服务
     * @return Observer
     */
    protected abstract fun startService(): Observer

    /**
     * 检测异常
     * @return Throwable?
     */
    protected abstract fun checkException(): Throwable?
}