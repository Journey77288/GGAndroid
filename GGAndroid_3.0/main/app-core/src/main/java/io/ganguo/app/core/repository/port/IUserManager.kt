package io.ganguo.app.core.repository.port

import io.reactivex.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/11
 *     desc   : UserManager interface
 * </pre>
 */
interface IUserManager<T> {


    /**
     * init rider info
     */
    fun initUser()


    /**
     * Query rider data from the database
     * `If no rider data is queried from the database, an empty rider data object is created`
     * @return T
     */
    fun queryUserFromDatabase(): T


    /**
     * update rider info
     * @param data
     */
    fun updateUser(data: T) {
    }

    /**
     * rider logout
     */
    fun logout() {
    }

    /**
     * rider login success
     * @param data T
     */
    fun login(data: T)

    /**
     * rider is login
     * @return
     */
    fun isLogin(): Boolean

    /**
     * rider token from current login rider
     * @return
     */
    fun getUserToken(): String


    /**
     * go to login page
     */
    fun jumpLoginPage()

    /**
     * get rider Obserable
     *
     * @return Observable<T>
     */
    fun getUserObservable(): Observable<T>

    /**
     * If logged in, the [func] function is executed, and if not, the jump login function is called
     * @param func
     */
    fun isLoginOrFunc(func: () -> Unit) {
        if (!isLogin()) {
            jumpLoginPage()
        } else {
            func()
        }
    }
}