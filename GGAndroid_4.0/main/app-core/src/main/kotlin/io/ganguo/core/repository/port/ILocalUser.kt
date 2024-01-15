package io.ganguo.core.repository.port

import io.reactivex.rxjava3.core.Observable


/**
 * <pre>
 *     @author : leo
 *     time   : 2020/02/12
 *     desc   : UserManager interface
 * </pre>
 */
interface ILocalUser<T : Any> {
    var data: T

    /**
     * init rider info
     */
    fun initUser()


    /**
     * Query rider data from the database
     * `If no rider data is queried from the database, an empty rider data object is created`
     * @return T
     */
    fun queryUser(): T


    /**
     * update rider info
     * @param data
     */
    fun updateUser(data: T) {
        this.data = data
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
    fun login(data: T) {
        this.data = data
    }

    /**
     * rider is login
     * @return
     */
    fun isLogin(): Boolean

    /**
     * Gets the logon user Token
     * @return
     */
    fun getToken(): String


    /**
     * Start the login Flow
     */
    fun startLoginFlow()

    /**
     * user data to Observable
     * @return Observable<T>
     */
    fun toObservable(): Observable<T>

    /**
     * Perform the login or the next process
     * @param nextFlow
     */
    fun executeLoginOrNextFlow(nextFlow: (T) -> Unit) {
        if (!isLogin()) {
            startLoginFlow()
        } else {
            nextFlow(data)
        }
    }
}
