package io.ganguo.factory.result

import io.reactivex.subjects.Subject

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : 通过RxJava发射数据
 * </pre>
 */
interface IRxResultSend<T> {
    var resultEmitter: Subject<T>?

    /**
     * 通过[Subject]发射数据
     * @param data
     */
    fun sendResult(data: T) {
        if (resultEmitter == null) {
            throw RuntimeException("${this.javaClass.simpleName} resultEmitter cannot be null!!!")
        }
        resultEmitter!!.onNext(data)
        resultEmitter!!.onComplete()
    }
}