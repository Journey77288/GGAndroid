package io.ganguo.core.helper.activity

import androidx.activity.ComponentActivity

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/18
 *     desc   : Activity 数据获取接口
 * </pre>
 */
interface ActivityObtain<T : ComponentActivity> {


    /**
     * Get the previous activity
     * @return
     */
    fun getPreviousActivity(): ComponentActivity?


    /**
     * Gets the current page Activity
     * @return
     */
    fun currentActivity(): ComponentActivity?

    /**
     * Close all ComponentActivity in the container
     */
    fun finishAllActivity()

    /**
     * Close ComponentActivity in the container
     * @param activity
     * @return
     */
    fun finishActivity(activity: T): Boolean


    /**
     * Close a page of type Class<Activity>
     * @param clazz Class<out Activity>
     * @return Boolean
     */
    fun finishActivity(clazz: Class<T>): Boolean


    /**
     * Get Activity by type Class<ComponentActivity>
     * @param clazz Class<Activity>
     * @return Boolean
     */
    fun getActivity(clazz: Class<out T>): List<T>


    /**
     * Gets the first element of type Class<R> ComponentActivity in the container
     * @param clazz Class<out ComponentActivity>
     * @return Boolean
     */
    fun <R : T> getFirstActivity(clazz: Class<R>): R?

    /**
     * Gets the last element of type Class<R> ComponentActivity in the container
     * @param clazz Class<out ComponentActivity>
     * @return Boolean
     */
    fun <R : T> getLastActivity(clazz: Class<R>): R?


    /**
     * Gets global the first stored ComponentActivity
     * @return T
     */
    fun <R : T> getFirstActivity(): R?

    /**
     * Gets global the last stored ComponentActivity
     * @return T
     */

    fun <R : T> getLastActivity(): R?


    /**
     * Gets all Activity in the container
     * @return R
     */
    fun getAllActivity(): MutableList<T>
}