package io.ganguo.incubator.repository

import io.ganguo.app.core.http.response.UserInfoResponse
import io.ganguo.app.core.repository.AbstractLocalUser


/**
 * <pre>
 *     author : leo
 *     time   : 2019/05/27
 *     desc   : 用户信息管理
 * </pre>
 * @property userEntity 用户Preference数据类
 * @property token 用户token
 */
class LocalUser private constructor() : AbstractLocalUser() {

    /**
     * 从数据库中查询用户数据
     * @return com.jsrs.common.http.response.UserInfoResponse
     */
    override fun queryUserFromDatabase(): UserInfoResponse {
        TODO("Not yet implemented")
    }


    /**
     * 跳转到登录页面
     * @return
     */
    override fun jumpLoginPage() {
        //todo 这里写跳转登录方法
    }


    /**
     * 退出登录
     */
    override fun logout() {
        TODO("更新内存缓存和数据库")
    }

    /**
     * 登录成功，更新用户数据
     * @param data UserInfoResponse
     */
    override fun login(data: UserInfoResponse) {
        updateUser(data)
        TODO("Not yet implemented")
    }


    companion object {
        private val INSTANCE: LocalUser by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LocalUser()
        }

        /**
         * 单例对象
         * @return
         */
        @JvmStatic
        fun get(): LocalUser {
            return INSTANCE
        }
    }
}