package io.ganguo.rxjava.port

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/18
 *   @desc   : RxJava builder interface
 * </pre>
 * @property toast Function1<String, Unit> Toast提示接口函数
 * @property throwable Function1<Throwable, Unit> 异常捕获回调函数
 * @property logger Function1<String, Unit> 打印函数
 */
interface IRxJavaBuilder {
    var toast: ((String) -> Unit)?
    var throwable: ((Throwable) -> Unit)?
    var logger: ((String) -> Unit)?
}