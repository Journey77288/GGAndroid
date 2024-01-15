package io.ganguo.core.repository

import androidx.databinding.ObservableField
import io.ganguo.core.repository.port.ILocalUser
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * <pre>
 *     @author : leo
 *     time   : 2020/02/12
 *     desc   : AbstractLocalUser
 * </pre>
 */
abstract class AbstractLocalUser<T : Any> : ILocalUser<T> {
    protected open var publishSubject: PublishSubject<T> = PublishSubject.create()
    protected open lateinit var user: ObservableField<T>
    protected open val token: ObservableField<String> = ObservableField("")

    init {
        initUser()
    }


    /**
     * to User Observable
     * @return Observable<T>
     */
    override fun toObservable(): Observable<T> = let {
        publishSubject.toFlowable(BackpressureStrategy.BUFFER).toObservable()
    }

    /**
     * 获取用户登录Token
     * @return String
     */
    override fun getToken(): String {
        return token.get().orEmpty()
    }


    /**
     * 用户是否已经登录
     * @return
     */
    override fun isLogin(): Boolean {
        return token.get().orEmpty().isNotEmpty()
    }


}
