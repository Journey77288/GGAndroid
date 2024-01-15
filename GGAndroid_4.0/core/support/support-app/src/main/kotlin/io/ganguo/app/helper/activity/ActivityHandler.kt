package io.ganguo.app.helper.activity

import android.app.Activity
import androidx.activity.ComponentActivity

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/18
 *     desc   : Activity Stack Manager Helper
 * </pre>
 */
@Suppress("UNCHECKED_CAST")
class ActivityHandler(override val container: MutableList<Activity>) :
        ActivityObtain<Activity>, ActivitiesContainer<Activity> {

    /**
     * Close all ComponentActivity in the container
     */
    override fun finishAllActivity() {
        forEach {
            it.finish()
        }.let {
            clear()
        }
    }

    /**
     * Close ComponentActivity in the container
     * @param activity
     * @return
     */
    override fun finishActivity(activity: Activity): Boolean {
        return activity
                .takeIf {
                    remove(activity)
                }.let {
                    it != null
                }
    }

    /**
     * Close a page of type Class<Activity>
     * @param clazz Class<out Activity>
     * @return Boolean
     */
    override fun finishActivity(clazz: Class<Activity>): Boolean {
        return this
                .filter {
                    clazz == it.javaClass
                }.map {
                    it.finish()
                    it
                }.let {
                    removeAll(it.asIterable())
                }
    }

    /**
     * Get Activity by type Class<ComponentActivity>
     * @param clazz Class<Activity>
     * @return Boolean
     */
    override fun getActivity(clazz: Class<out Activity>): List<Activity> {
        return this
                .filter {
                    clazz == it.javaClass
                }
                .toList()
    }

    /**
     * Gets the first element of type Class<R> ComponentActivity in the container
     * @param clazz Class<out ComponentActivity>
     * @return Boolean
     */
    override fun <R : Activity> getFirstActivity(clazz: Class<R>): R? {
        return this.getActivity(clazz)
                .firstOrNull()
                ?.let {
                    it as R
                }
    }

    /**
     * Gets global the first stored ComponentActivity
     * @return T
     */
    override fun <R : Activity> getFirstActivity(): R? {
        if (isEmpty()) return null
        return first().let {
            it as R
        }
    }

    /**
     * Gets global the last stored ComponentActivity
     * @return T
     */
    override fun <R : Activity> getLastActivity(): R? {
        if (isEmpty()) return null
        return last().let {
            it as R
        }
    }

    /**
     * Gets the last element of type Class<R> ComponentActivity in the container
     * @param clazz Class<out ComponentActivity>
     * @return Boolean
     */
    override fun <R : Activity> getLastActivity(clazz: Class<R>): R? {
        return this.getActivity(clazz)
                .lastOrNull()
                ?.let {
                    it as R
                }
    }

    /**
     * Determine whether type ComponentActivity exists in the current container
     * @param clazz Class<out ComponentActivity>
     * @return Boolean
     */
    fun contains(clazz: Class<out ComponentActivity>): Boolean {
        return any {
            clazz == it.javaClass
        }
    }


    /**
     * Gets all ComponentActivity in the container
     * @return MutableList<ComponentActivity>
     */
    override fun allActivity(): MutableList<Activity> {
        return this.container
    }

    /**
     * Get the previous activity
     * @return
     */
    override fun getPreviousActivity(): Activity? {
        return takeIf {
            size > 2
        }?.indexOf(currentActivity())?.takeIf {
            it != -1 && it < size
        }?.let {
            this[it]
        }
    }


    /**
     * Gets the current page Activity
     * @return
     */
    override fun currentActivity(): Activity? {
        return getLastActivity()
    }
}
