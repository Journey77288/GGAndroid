package io.ganguo.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.functions.Functions
import java.util.concurrent.TimeUnit

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/01/22
 *     desc   : 倒计时工具类
 * </pre>
 */
object Times {
    /**
     * 启动倒计时
     *
     * @param second Int 时间
     * @param tickAction Action1<Int>? 倒计时每秒操作 返回剩余秒数
     * @param finishAction Action? 倒计时结束操作
     * @return Disposable
     */
    fun startCountDown(second: Int, tickAction: Action1<Int>? = null, finishAction: Action ?= null): Disposable = let {
        val time = second + 1L
        Observable.intervalRange(0, time, 0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { tickAction?.invoke((second - it).toInt()) }
            .doOnComplete { finishAction?.invoke() }
            .subscribe(Functions.emptyConsumer(), { it.printStackTrace() })
    }
}