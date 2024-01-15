package io.ganguo.factory.result

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   : IRxResultEmitter
 * </pre>
 * @property resultSubject 用于发射数据
 * @property resultObserver 用于观察数据的变更
 */
interface IRxResultEmitter<Subject, Observer> {
    val resultSubject: Subject
    val resultObserver: Observer
}