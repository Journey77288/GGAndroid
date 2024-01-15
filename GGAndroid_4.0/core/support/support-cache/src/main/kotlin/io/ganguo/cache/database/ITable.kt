package io.ganguo.cache.database

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/28
 *     desc   : Database Table interface
 * </pre>
 */
interface ITable {


    /**
     * Insert multiple pieces of data into the database
     * @return Long
     */
    fun insert(): Long


    /**
     * Insert a single piece of data into the database
     * @return Long
     */
    fun insertResultStatus(): Boolean


    /**
     * Update multiple data to the database,
     * note that the database does not exist in the data, will automatically create
     * @return Long
     */
    fun update(): Long


    /**
     * Inserts a single piece of data into the database and returns the saved state
     * @return Boolean
     */
    fun updateResultStatus(): Boolean {
        return update() > 0
    }


    /**
     * Deletes a single piece of data from the database and returns the delete status
     * @return Boolean
     */
    fun remove(): Boolean


}