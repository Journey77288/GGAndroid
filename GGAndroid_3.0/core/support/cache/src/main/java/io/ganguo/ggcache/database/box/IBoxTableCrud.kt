@file:Suppress("UNCHECKED_CAST")

package io.ganguo.ggcache.database.box

import io.ganguo.ggcache.database.ITableCrud
import io.objectbox.Box
import io.objectbox.query.Query
import io.objectbox.query.QueryBuilder
import io.objectbox.rx.RxQuery
import io.reactivex.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/26
 *     desc   : Object Box Table Crud Helper interface
 * </pre>
 */
interface IBoxTableCrud<T> : ITableCrud<T> {
    val tableClass: Class<T>

    /**
     * Gets the Table operation instance
     * @return
     */
    fun <T> box(): Box<T> {
        return BoxHelper.box(tableClass) as Box<T>
    }

    /**
     * Insert multiple pieces of data into the database
     * @param tables List<T>
     */
    override fun insertAll(tables: List<T>) {
        box<T>().put(tables)
    }

    /**
     * Insert a single piece of data into the database
     * @param table T
     * @return Long
     */
    override fun insert(table: T): Long {
        return box<T>().put(table)
    }

    /**
     * Inserts a single piece of data into the database and returns the saved state
     * @param table T
     * @return Boolean
     */
    override fun insertResultStatus(table: T): Boolean {
        return insert(table) > 0
    }

    /**
     * Update multiple data to the database,
     * note that the database does not exist in the data, will automatically create
     * @param tables List<T>
     */
    override fun updateAll(tables: List<T>) {
        return insertAll(tables)
    }

    /**
     * Update single data to database
     * @param table T
     * @return Long
     */
    override fun update(table: T): Long {
        return insert(table)
    }

    /**
     * Update single data to database and returns the saved state
     * @param table T
     * @return Boolean
     */
    override fun updateResultStatus(table: T): Boolean {
        return insertResultStatus(table)
    }

    /**
     * Deletes a single piece of data from the database and returns the delete status
     * @param table T
     * @return Boolean
     */
    override fun remove(table: T): Boolean {
        return box<T>().remove(table)
    }

    /**
     * Clean the current table type data.
     * Careful operation, All table data of this type [T] will be deleted.
     */
    override fun clear() {
        box<T>().removeAll()
    }

    /**
     * Deletes multiple pieces of data from the database
     * @param tables List<T>
     */
    override fun removeAll(tables: List<T>) {
        box<T>().remove(tables)
    }

    /**
     * Delete the data whose id is equal to [tableId] and return the delete status.
     * @param tableId Long
     * @return Boolean
     */
    override fun remove(tableId: Long): Boolean {
        return box<T>().remove(tableId)
    }

    /**
     * Query all data of type [T] from the database
     * @return List<T>
     */
    override fun queryAll(): List<T> {
        return box<T>().all
    }


    /**
     * Query a single piece of data from the database based on the id
     * @param tableId Long
     * @return T
     */
    override fun query(tableId: Long): T {
        return box<T>()[tableId]
    }

    /**
     * Query type [T] from the database, the first data
     * @return T?
     */
    override fun queryFirst(): T? {
        return box<T>().all.first()
    }


    /**
     * According to [queryBuilder], query the data from the database
     * @param queryBuilder QueryBuilder<T>
     * @return List<T>
     */
    fun queryAll(queryBuilder: QueryBuilder<T>): List<T> {
        return queryBuilder.build().find()
    }

    /**
     * According to [queryBuilder], query the data from the database, Paging queries are also supported.
     * @param offset Long
     * @param limit Long
     * @param queryBuilder QueryBuilder<T>
     * @return List<T>
     */
    fun queryAll(offset: Long, limit: Long, queryBuilder: QueryBuilder<T>): List<T> {
        return queryBuilder.build().find(offset, limit)
    }

    /**
     *
     * @param queryBuilder QueryBuilder<T>
     * @return T?
     */
    fun queryFirst(queryBuilder: QueryBuilder<T>): T? {
        return queryBuilder.build().findFirst()
    }


    /**
     * According to [queryBuilder], Query type [T] and return the first data in the result
     * @return QueryBuilder<T>
     */
    fun queryBuilder(): QueryBuilder<T> {
        return box<T>().query()
    }


    /**
     * Create a data query object based on [queryBuilder]
     * @param queryBuilder QueryBuilder<T>
     * @return Query<T>
     */
    fun query(queryBuilder: QueryBuilder<T>): Query<T> {
        return queryBuilder.build()
    }

    /**
     * Create a data query object
     * @return Query<T>
     */
    fun query(): Query<T> {
        return queryBuilder().build()
    }

    /**
     * Observe the change of data through RxJava
     * @return
     */
    fun asRxQuery(): Observable<List<T>> {
        return asRxQuery(queryBuilder())
    }

    /**
     * Observe the change of data through RxJava，Support for adding query conditions.
     * @param queryBuilder QueryBuilder<T>
     * @return Observable<List<T>>
     */
    fun asRxQuery(queryBuilder: QueryBuilder<T>): Observable<List<T>> {
        return RxQuery.observable(queryBuilder.build())
    }
}
