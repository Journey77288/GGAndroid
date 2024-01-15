package io.ganguo.incubator.repository

import android.annotation.SuppressLint
import io.ganguo.core.repository.AbstractLocalUser
import io.ganguo.incubator.database.table.UserInfoTable
import io.ganguo.log.core.Logger
import io.reactivex.rxjava3.internal.functions.Functions


/**
 * <pre>
 *     author : leo
 *     time   : 2019/05/27
 *     desc   : 用户本地数据管理类
 * </pre>
 * @property data UserInfoTable
 */
class LocalUser private constructor() : AbstractLocalUser<UserInfoTable>() {
    override lateinit var data: UserInfoTable

    /**
     * 初始化本地用户数据
     */
    @SuppressLint("CheckResult")
    override fun initUser() {
        user.set(queryUser())
        publishSubject.onNext(user.get())
        toObservable()
                .doOnNext {
                    updateUser(it)
                    Logger.e("UserEntity:$it")
                }
                .subscribe(Functions.emptyConsumer())

    }

    /**
     * 从本地数据库查询用户数据
     * @return UserInfoTable
     */
    override fun queryUser(): UserInfoTable {
        return UserInfoTable()
    }

    /**
     * 启动用户登录流程
     */
    override fun startLoginFlow() {
    }


    /**
     * 伴生对象，单例类
     */
    companion object {
        private val INSTANCE: LocalUser by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LocalUser()
        }

        @JvmStatic
        fun get(): LocalUser {
            return INSTANCE
        }
    }
}


