package io.ganguo.cache.database

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/26
 *     desc   : Database Table Crud Helper interface
 * </pre>
 */
interface ITableCrud<T> {


    /**
     * Insert multiple pieces of data into the database
     * @param tables List<T>
     */
    fun insertAll(tables: List<T>)

    /**
     * Insert multiple pieces of data into the database
     * @param table T
     * @return Long
     */
    fun insert(table: T)

    /**
     * Update multiple data to the database,
     * note that the database does not exist in the data, will automatically create
     * @param tables List<T>
     */
    fun updateAll(tables: List<T>)

    /**
     * Inserts a single piece of data into the database and returns the saved state
     * @param table T
     * @return Boolean
     */
    fun update(table: T)

    /**
     * Deletes a single piece of data from the database and returns the delete status
     * @param table T
     * @return Boolean
     */
    fun remove(table: T)

    /**
     * Clean the current table type data.
     * Careful operation, All table data of this type [T] will be deleted.
     */
    fun clear()

    /**
     * Deletes multiple pieces of data from the database
     * @param tables List<T>
     */
    fun removeAll(tables: List<T>)


    /**
     * Delete the data whose id is equal to [tableId] and return the delete status.
     * @param tableId Long
     * @return Boolean
     */
    fun remove(tableId: Long): Boolean


    /**
     * Query all data of type [T] from the database
     * @return List<T>
     */
    fun queryAll(): List<T>


    /**
     * 根据id读取一条数据库数据，前提是该数据库已有该条数据，并且已知id的情况下才能用
     *
     * @return
     */
    fun query(tableId: Long): T


    /**
     * 根据Class类型查询第一条数据
     *
     * @return
     */
    fun queryFirst(): T?

}