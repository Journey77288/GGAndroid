@file:JvmName("RxAction")

package io.ganguo.rxjava

import io.reactivex.rxjava3.functions.Consumer

/**
 * <pre>
 *   @author : leo
 *    time   : 2020/08/21
 *    desc   : RxAction
 * </pre>
 */

/**
 * 错误异常打印
 * @param tag String 标识，最好用调用函数名称
 * @return Consumer<Throwable>
 */
@Deprecated("废弃，请使用printThrowable")
fun errorPrint(tag: String): Consumer<Throwable> {
    return Consumer {
        it.printStackTrace()
        RxHelper.get().logger?.invoke("errorThrowable:${tag}:${it.message}")
    }
}

/**
 * 打印RxJava异常
 * @param tag String
 * @return Consumer<Throwable>
 */
fun printThrowable(tag: String): Consumer<Throwable> {
    return Consumer {
        it.printStackTrace()
        RxHelper.get().logger?.invoke("errorThrowable:${tag}:${it.message}")
    }
}

