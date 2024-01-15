package io.ganguo.app.core.repository

import io.ganguo.app.core.http.response.UserInfoResponse
import io.ganguo.app.core.repository.port.IUserManager
import io.ganguo.log.core.Logger
import io.ganguo.rx.RxActions
import io.ganguo.rx.RxProperty
import io.ganguo.utils.util.Strings
import io.reactivex.Observable
import io.reactivex.internal.functions.Functions

/**
 * <pre>
 *     author : leo
 *     time   : 2019/05/27
 *     desc   : 用户信息管理
 * </pre>
 * @property token 用户token
 */
abstract class AbstractLocalUser : IUserManager<UserInfoResponse> {
    private lateinit var userEntity: RxProperty<UserInfoResponse>
    private var token: RxProperty<String> = RxProperty()

    init {
        initUser()
    }

    /**
     * 初始化用户信息
     */
    @Synchronized
    final override fun initUser() {
        userEntity = RxProperty(queryUserFromDatabase())
        userEntity
                .doOnNext {
                    Logger.e("UserEntity:$it")
                    token.set(it.apiToken)
                }
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable("--initUserEntity--"))
    }

    /**
     * 获取用户Observable对象
     *
     * @return Observable<UserEntity>
     */
    override fun getUserObservable(): Observable<UserInfoResponse> {
        return userEntity
    }


    /**
     * 获取用户Token
     * @return
     */
    override fun getUserToken(): String {
        return token.get().orEmpty()
    }


    /**
     * 更新用户信息
     * @param user
     */
    @Synchronized
    override fun updateUser(data: UserInfoResponse) {
        userEntity.set(data)
        TODO("更新内存缓存和数据库")
    }


    /**
     * 用户是否登录
     * @return
     */
    override fun isLogin(): Boolean {
        return Strings.isNotEmpty(token.get())
    }


}