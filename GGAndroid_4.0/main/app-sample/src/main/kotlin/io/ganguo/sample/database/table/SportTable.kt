package io.ganguo.sample.database.table

import io.ganguo.cache.database.box.IBoxTable
import io.ganguo.cache.database.box.IBoxTableCrud
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.annotation.NameInDb
import io.objectbox.query.QueryBuilder

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/08
 *     desc   : local Sport Database Table
 * </pre>
 */
@Entity
class SportTable : IBoxTable {
    @Id
    @NameInDb("id")
    var id: Long = 0

    @Index
    @NameInDb("name")
    var name: String = ""

    companion object : IBoxTableCrud<SportTable> {
        override val tableClass: Class<SportTable> by lazy {
            SportTable::class.java
        }

        /**
         * Get data by name
         *
         * @param name
         * @return SportTable
         */
        fun getTableByName(name: String): SportTable {
            return queryBuilder()
                    .equal(SportTable_.name, name, QueryBuilder.StringOrder.CASE_SENSITIVE)
                    .build()
                    .findFirst() ?: SportTable()
        }

        /**
         * Remove data by name
         *
         * @param name
         */
        fun removeByName(name: String) {
            val table = getTableByName(name)
            remove(table)
        }
    }
}