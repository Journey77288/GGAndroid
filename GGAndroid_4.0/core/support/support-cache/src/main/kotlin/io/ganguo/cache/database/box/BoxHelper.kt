package io.ganguo.cache.database.box

import io.objectbox.Box
import io.objectbox.BoxStore

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/27
 *     desc   : ObjectBox Database BoxHelper
 * </pre>
 */
object BoxHelper {
    private lateinit var boxStore: BoxStore

    /**
     * Gets the data table operation instance
     * @param clazz Class<T>
     * @return Box<T>
     */
    @JvmStatic
    fun <T> box(clazz: Class<T>): Box<T> {
        return store().boxFor(clazz)
    }


    /**
     *  init Object BoxStore
     * @param store BoxStore
     */
    @JvmStatic
    fun initialize(store: BoxStore) {
        check(!BoxHelper::boxStore.isInitialized) { "Already initialized" }
        boxStore = store
    }


    /**
     * get BoxStore
     *
     * @return
     */
    @JvmStatic
    private fun store(): BoxStore {
        check(BoxHelper::boxStore.isInitialized) { "Call init BoxHelper at Application! " }
        return boxStore
    }

}