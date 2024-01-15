package io.ganguo.ggcache.database.box

import io.ganguo.ggcache.database.ITable
import io.objectbox.Box

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/26
 *     desc   : ObjectBox Table interface
 * </pre>
 */
interface IBoxTable : ITable {
    /**
     * Gets the Table operation instance
     * @param clazz
     * @return
     */
    fun <T> box(clazz: Class<T>): Box<T> {
        return BoxHelper.box(clazz)
    }

    /**
     * Insert a single piece of data into the database
     * @return Long
     */
    override fun insert(): Long {
        return box(javaClass).put(this)
    }


    /**
     * Inserts a single piece of data into the database and returns the saved state
     * @return Boolean
     */
    override fun insertResultStatus(): Boolean {
        return insert() > 0
    }


    /**
     * Update multiple data to the database,
     * note that the database does not exist in the data, will automatically create
     * @return Long
     */
    override fun update(): Long {
        return insert()
    }


    /**
     * Inserts a single piece of data into the database and returns the saved state
     * @return Boolean
     */
    override fun updateResultStatus(): Boolean {
        return update() > 0
    }


    /**
     * 删除一条数据
     *
     * @return
     */
    /**
     * Deletes a single piece of data from the database and returns the delete status
     * @return Boolean
     */
    override fun remove(): Boolean {
        return box(javaClass).remove(this)
    }


}